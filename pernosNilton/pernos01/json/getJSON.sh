#!/bin/bash

PDF="procesar.pdf"
TABULA_JAR="tabula.jar"
mkdir -p json_salida

TOTAL=$(pdfinfo "$PDF" | grep Pages | awk '{print $2}')

for ((i=1; i<=TOTAL; i++)); do
  echo "Procesando página $i..."
  java -jar "$TABULA_JAR" -p "$i" -f JSON -o "json_salida/pagina${i}.json" "$PDF"
done

echo "Listo. Archivos JSON guardados en 'json_salida/'."
