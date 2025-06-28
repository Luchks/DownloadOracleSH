#!/bin/bash

set -e

# === CONFIGURACIÓN ===
ORACLE_USER=oracle
ORACLE_GROUP=oinstall
ORACLE_HOME=/opt/oracle/product/21c/dbhome_1
ORACLE_BASE=/opt/oracle
ORACLE_INVENTORY=/opt/oraInventory
ORACLE_SID=ORCL
RPM_FILE="$HOME/Downloads/oracle-database-ee-21c-1.0-1.ol7.x86_64.rpm"
EXTRACT_DIR="$HOME/oracle_rpm_extract"

echo "==> Verificando que rpmextract.sh esté instalado..."
if ! command -v rpmextract.sh &>/dev/null; then
    echo "❌ rpmextract.sh no está instalado. Instálalo con:"
    echo "   yay -S rpmextract"
    exit 1
fi

echo "==> Verificando que el archivo RPM exista..."
if [[ ! -f "$RPM_FILE" ]]; then
    echo "❌ No se encontró $RPM_FILE"
    echo "   Asegúrate de haberlo descargado desde Oracle y movido a tu carpeta personal."
    exit 1
fi

echo "==> Instalando dependencias necesarias..."
sudo pacman -Syu --noconfirm unzip gcc binutils make libaio bc libnsl cpio

echo "==> Creando usuario y grupos..."
sudo groupadd -f $ORACLE_GROUP
sudo useradd -m -g $ORACLE_GROUP -G dba $ORACLE_USER || true
echo "$ORACLE_USER:oracle" | sudo chpasswd

echo "==> Creando directorios de instalación..."
sudo mkdir -p $ORACLE_HOME $ORACLE_INVENTORY
sudo chown -R $ORACLE_USER:$ORACLE_GROUP $ORACLE_BASE $ORACLE_INVENTORY
sudo chmod -R 775 $ORACLE_BASE $ORACLE_INVENTORY

echo "==> Extrayendo el archivo RPM en un directorio limpio..."
rm -rf "$EXTRACT_DIR"
mkdir -p "$EXTRACT_DIR"
cd "$EXTRACT_DIR"
rpmextract.sh "$RPM_FILE"

# Verificar si la extracción fue exitosa
if [[ ! -d "etc/opt/oracle" ]] || [[ ! -d "opt/oracle" ]]; then
    echo "❌ La extracción del RPM no generó las carpetas esperadas."
    echo "   Puede que el archivo esté corrupto o no sea el correcto."
    echo "   Verifica que descargaste el RPM de Oracle 21c completo (unos 2.5 GB)."
    exit 1
fi

echo "==> Copiando archivos al ORACLE_HOME..."
sudo cp -r etc/opt/oracle $ORACLE_BASE
sudo cp -r opt/oracle/* $ORACLE_BASE
sudo chown -R $ORACLE_USER:$ORACLE_GROUP $ORACLE_BASE

echo "==> Creando enlaces simbólicos para compatibilidad..."
sudo ln -sf /usr/lib/libnsl.so.2 /usr/lib/libnsl.so.1 || true
sudo ln -sf /usr/lib/libstdc++.so.6 /usr/lib/libstdc++.so.5 || true

echo "==> Configurando entorno del usuario Oracle..."
sudo tee /home/$ORACLE_USER/.bash_profile > /dev/null <<EOF
export ORACLE_BASE=$ORACLE_BASE
export ORACLE_HOME=$ORACLE_HOME
export ORACLE_SID=$ORACLE_SID
export PATH=\$ORACLE_HOME/bin:\$PATH
EOF
sudo chown $ORACLE_USER:$ORACLE_GROUP /home/$ORACLE_USER/.bash_profile

echo "==> Oracle RPM extraído y copiado correctamente."
echo "🔄 Ahora puedes continuar con la instalación ejecutando ./runInstaller como el usuario oracle."

