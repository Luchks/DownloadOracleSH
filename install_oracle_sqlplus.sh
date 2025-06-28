#!/bin/bash
set -e

# Variables
INSTALL_DIR="$HOME/oracle/instantclient"
CLIENT_VERSION="21_18"
CLIENT_ZIP_VERSION="21.18.0.0.0dbru"
BASIC_URL="https://download.oracle.com/otn_software/linux/instantclient/2118000/instantclient-basic-linux.x64-${CLIENT_ZIP_VERSION}.zip"
SQLPLUS_URL="https://download.oracle.com/otn_software/linux/instantclient/2118000/instantclient-sqlplus-linux.x64-${CLIENT_ZIP_VERSION}.zip"
TARGET_DIR="${INSTALL_DIR}/instantclient_${CLIENT_VERSION}"

# Crear directorio de instalación
echo "📁 Creando carpeta: $INSTALL_DIR"
mkdir -p "$INSTALL_DIR"
cd "$INSTALL_DIR"

# Instalar dependencias necesarias
echo "📦 Instalando dependencias requeridas..."
sudo pacman -S --needed unzip curl libaio

# Descargar archivos ZIP
echo "🌐 Descargando Oracle Instant Client Basic..."
curl -L -O "$BASIC_URL"
echo "🌐 Descargando Oracle Instant Client SQL*Plus..."
curl -L -O "$SQLPLUS_URL"

# Extraer ZIPs
echo "📦 Extrayendo paquetes..."
unzip -o "instantclient-basic-linux.x64-${CLIENT_ZIP_VERSION}.zip"
unzip -o "instantclient-sqlplus-linux.x64-${CLIENT_ZIP_VERSION}.zip"

# Agregar al .bashrc o .zshrc
echo "🔧 Configurando variables de entorno..."

ENV_LINE1="export PATH=${TARGET_DIR}:\$PATH"
ENV_LINE2="export LD_LIBRARY_PATH=${TARGET_DIR}:\$LD_LIBRARY_PATH"

# Detectar shell
SHELL_RC="$HOME/.bashrc"
if [[ $SHELL == */zsh ]]; then
  SHELL_RC="$HOME/.zshrc"
fi

# Evitar duplicados
grep -qxF "$ENV_LINE1" "$SHELL_RC" || echo "$ENV_LINE1" >> "$SHELL_RC"
grep -qxF "$ENV_LINE2" "$SHELL_RC" || echo "$ENV_LINE2" >> "$SHELL_RC"

# Cargar entorno actual
export PATH="${TARGET_DIR}:$PATH"
export LD_LIBRARY_PATH="${TARGET_DIR}:$LD_LIBRARY_PATH"

# Verificar instalación
echo "🔍 Verificando sqlplus..."
if command -v sqlplus >/dev/null 2>&1; then
  sqlplus -v
  echo "✅ sqlplus instalado correctamente."
else
  echo "❌ No se pudo encontrar sqlplus en el PATH."
fi

echo -e "\n🧠 Ejecuta 'source $SHELL_RC' o reinicia tu terminal para activar las variables."
echo "🔌 Luego prueba con: sqlplus usuario/password@//localhost:1521/XEPDB1"

