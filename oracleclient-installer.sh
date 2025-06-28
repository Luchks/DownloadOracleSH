#!/bin/bash

echo "🔧 Instalando Oracle Instant Client en Arch Linux..."

# Detecta la carpeta de quien ejecutó sudo (o del usuario normal si no se usó sudo)
USER_HOME=$(eval echo "~$SUDO_USER")
DOWNLOADS_DIR="$USER_HOME/Downloads"

# Buscar los archivos ZIP en la carpeta de descargas
BASIC_ZIP=$(find "$DOWNLOADS_DIR" -iname "instantclient-basic-linux*.zip" | head -n 1)
SDK_ZIP=$(find "$DOWNLOADS_DIR" -iname "instantclient-sdk-linux*.zip" | head -n 1)

# Verificación
if [[ ! -f "$BASIC_ZIP" || ! -f "$SDK_ZIP" ]]; then
  echo "❌ No se encontraron los archivos ZIP de Oracle Instant Client en $DOWNLOADS_DIR"
  exit 1
fi

# Directorio de destino
DEST="/opt/oracle/instantclient"
sudo mkdir -p "$DEST"
sudo unzip -o "$BASIC_ZIP" -d "$DEST"
sudo unzip -o "$SDK_ZIP" -d "$DEST"

# Detectar la carpeta real instantclient_21_*
CLIENT_DIR=$(find "$DEST" -type d -name "instantclient_*" | head -n 1)

if [[ ! -d "$CLIENT_DIR" ]]; then
  echo "❌ No se encontró el directorio descomprimido dentro de $DEST"
  exit 1
fi

cd "$CLIENT_DIR" || exit 1

echo "🔗 Creando enlaces simbólicos..."
sudo ln -sf libclntsh.so.21.1 libclntsh.so
sudo ln -sf libocci.so.21.1 libocci.so

echo "🧠 Configurando entorno en /etc/profile.d/oracle.sh..."
echo "export LD_LIBRARY_PATH=$CLIENT_DIR:\$LD_LIBRARY_PATH" | sudo tee /etc/profile.d/oracle.sh > /dev/null
echo "export PATH=$CLIENT_DIR:\$PATH" | sudo tee -a /etc/profile.d/oracle.sh > /dev/null
sudo chmod +x /etc/profile.d/oracle.sh

echo "✅ Oracle Instant Client instalado correctamente."
echo "💡 Ejecuta: source /etc/profile.d/oracle.sh"
