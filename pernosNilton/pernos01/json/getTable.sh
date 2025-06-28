#!/bin/bash

mkdir -p tablas_csv

for file in json_salida/*.json; do
  page=$(basename "$file" .json | sed 's/pagina//')
  count=$(jq '. | length' "$file")
  echo "Página $page tiene $count tablas..."

  for ((i=0; i<count; i++)); do
    jq -r --argjson i $i '
      .[$i].data | map([.[].text]) | 
      map(@csv) | .[]
    ' "$file" > "tablas_csv/tabla_p${page}_t${i}.csv"
  done
done

echo "Listo. Tablas individuales guardadas en 'tablas_csv/'."

