#!/bin/bash

# Crear estructura de carpetas
mkdir -p pdf_table_extractor/data

# Crear docker-compose.yml
cat > pdf_table_extractor/docker-compose.yml <<'EOF'
version: '3.8'

services:
  extractor:
    build: .
    volumes:
      - ./data:/app/data
    working_dir: /app
    command: ["python", "main.py"]
EOF

# Crear Dockerfile
cat > pdf_table_extractor/Dockerfile <<'EOF'
FROM python:3.10-slim

RUN apt-get update && apt-get install -y openjdk-17-jre wget

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . /app
WORKDIR /app
EOF

# Crear requirements.txt
cat > pdf_table_extractor/requirements.txt <<'EOF'
tabula-py==2.9.0
pandas
EOF

# Crear main.py
cat > pdf_table_extractor/main.py <<'EOF'
import tabula
import pandas as pd

pdf_path = "data/archivo.pdf"

print(f"Extrayendo tablas de {pdf_path}...")
tables = tabula.read_pdf(pdf_path, pages='all', multiple_tables=True)

for i, table in enumerate(tables):
    output_path = f"data/tabla_{i+1}.csv"
    table.to_csv(output_path, index=False)
    print(f"Tabla {i+1} guardada en {output_path}")
EOF

# Mensaje final
echo "Proyecto creado en pdf_table_extractor/"
echo "Copia tu archivo PDF a pdf_table_extractor/data/archivo.pdf"

