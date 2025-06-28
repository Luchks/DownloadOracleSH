#!/bin/bash

PDF="procesar.pdf"
PREFIX="salida"
TABULA_JAR="tabula.jar"

# Obtener número de páginas
TOTAL=$(pdfinfo "$PDF" | grep Pages | awk '{print $2}')

# Crear una carpeta para los resultados
mkdir -p salidas

# Extraer cada página por separado
for ((i=1; i<=TOTAL; i++)); do
  echo "Procesando página $i..."
  java -jar "$TABULA_JAR" -p "$i" -f CSV -o "salidas/${PREFIX}${i}.csv" "$PDF"
done

echo "Listo. Archivos guardados en la carpeta 'salidas/'."
