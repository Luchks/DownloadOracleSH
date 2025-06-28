#!/bin/bash
set -e

echo "🚀  Preparando Oracle XE en Docker…"

# Carpeta de trabajo
WORKDIR="$HOME/oracle-docker"
mkdir -p "$WORKDIR" && cd "$WORKDIR"

# Variables de entorno
cat > .env <<EOF
ORACLE_PASSWORD=mysecurepassword
APP_USER=myuser
APP_USER_PASSWORD=myuserpass
EOF

# docker-compose.yml
cat > docker-compose.yml <<'EOF'
services:
  oracle-xe:
    image: gvenzl/oracle-xe:21-slim
    container_name: oracle-xe
    restart: unless-stopped
    network_mode: host
    environment:
      ORACLE_PASSWORD: ${ORACLE_PASSWORD}
      APP_USER: ${APP_USER}
      APP_USER_PASSWORD: ${APP_USER_PASSWORD}
    volumes:
      - oracle-data:/opt/oracle/oradata
    shm_size: 1g
volumes:
  oracle-data:
EOF

# Cargar .env
source .env

# Eliminar contenedor anterior si existe
if docker ps -a --format '{{.Names}}' | grep -q '^oracle-xe$'; then
  echo "⚠️  Ya existe un contenedor oracle-xe. Eliminándolo…"
  docker rm -f oracle-xe
fi

echo "🐳  Levantando Oracle XE…"
docker-compose up -d

echo "⏳  Esperando a que Oracle diga 'DATABASE IS READY TO USE'…"
until docker logs oracle-xe 2>&1 | grep -q "DATABASE IS READY TO USE"; do
  sleep 2
done
echo "✅  Oracle está listo."

# Ajustar listener
echo "📢  Registrando servicios…"
docker exec oracle-xe bash -c "cat <<SQL | sqlplus -s / as sysdba
ALTER SYSTEM SET LOCAL_LISTENER='(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521))' SCOPE=BOTH;
ALTER SYSTEM REGISTER;
EXIT;
SQL"

# Crear usuario en XEPDB1
echo "👤  Verificando existencia del usuario ${APP_USER} en XEPDB1…"
USER_EXISTS=$(docker exec oracle-xe bash -c "echo \"
ALTER SESSION SET CONTAINER = XEPDB1;
SELECT COUNT(*) FROM dba_users WHERE username=UPPER('${APP_USER}');
\" | sqlplus -s / as sysdba" | tail -2 | head -1 | tr -d '[:space:]')

if [ \"$USER_EXISTS\" = \"0\" ]; then
  echo \"➕  Creando usuario ${APP_USER}…\"
  docker exec oracle-xe bash -c "cat <<SQL | sqlplus -s / as sysdba
ALTER SESSION SET CONTAINER = XEPDB1;
CREATE USER ${APP_USER} IDENTIFIED BY ${APP_USER_PASSWORD};
GRANT CONNECT, RESOURCE TO ${APP_USER};
ALTER USER ${APP_USER} DEFAULT TABLESPACE users QUOTA UNLIMITED ON users;
EXIT;
SQL"
else
  echo "✅  El usuario ${APP_USER} ya existe."
fi

# Conectar con usql
echo "🔌  Conectando con usql…"
if command -v usql >/dev/null 2>&1; then
  usql "oracle://${APP_USER}:${APP_USER_PASSWORD}@localhost:1521/XEPDB1" || {
    echo "❌  No se pudo conectar con usql. Revisa las credenciales."
  }
else
  echo "❌  usql no está instalado."
fi
