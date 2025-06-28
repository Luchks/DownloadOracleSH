#!/bin/bash
set -e

echo "🚀  Preparando Oracle XE en Docker…"

# Carpeta de trabajo
WORKDIR="$HOME/oracle-docker"
mkdir -p "$WORKDIR" && cd "$WORKDIR"

# .env
cat > .env <<EOF
ORACLE_PASSWORD=mysecurepassword
APP_USER=myuser
APP_USER_PASSWORD=myuserpass
EOF

# docker-compose.yml
cat > docker-compose.yml <<'EOF'
version: "3.8"

services:
  oracle-xe:
    image: gvenzl/oracle-xe:21-slim
    container_name: oracle-xe
    restart: unless-stopped
    network_mode: host               # evita bridge/veth
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

# Ajustar LOCAL_LISTENER y registrar servicios
echo "📢  Configurando LOCAL_LISTENER=localhost:1521 y registrando…"
docker exec oracle-xe bash -c "cat <<'SQL' | sqlplus -s / as sysdba
ALTER SYSTEM SET LOCAL_LISTENER='(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521))' SCOPE=BOTH;
ALTER SYSTEM REGISTER;
EXIT;
SQL"

# Extraer el primer SERVICE_NAME disponible
SERVICE_NAME=$(docker exec oracle-xe bash -c "lsnrctl status" | \
               grep -oP 'Service \"\K[^\"]+' | head -n 1)

if [ -z \"$SERVICE_NAME\" ]; then
  echo '❌  No se detectó SERVICE_NAME después del registro. Revisa el listener manualmente.'
  exit 1
fi

echo \"🔎  SERVICE_NAME detectado: $SERVICE_NAME\"

echo \"🔌  Intentando conectar con SQLcl…\"
sql myuser/myuserpass@//localhost:1521/$SERVICE_NAME || {
  echo '❌  No se pudo conectar. Verifica que SQLcl esté instalado y en PATH.'
}
