import tabula
import pandas as pd
import os
from datetime import datetime

pdf_path = "data/archivo.pdf"
pdf_name = os.path.splitext(os.path.basename(pdf_path))[0]
timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")

print(f"Extrayendo tablas de {pdf_path}...")

# Determinar el número total de páginas del PDF
from PyPDF2 import PdfReader
reader = PdfReader(pdf_path)
total_pages = len(reader.pages)

# Procesar cada página individualmente
table_counter = 1

for page_num in range(1, total_pages + 1):
    print(f"Procesando página {page_num}...")
    try:
        tables = tabula.read_pdf(
            pdf_path,
            pages=page_num,
            multiple_tables=True,
            pandas_options={'dtype': str}
        )

        for i, df in enumerate(tables):
            output_filename = f"data/{pdf_name}_page{page_num}_table{table_counter}_{timestamp}.csv"
            df.to_csv(output_filename, index=False)
            print(f"→ Tabla guardada en {output_filename}")
            table_counter += 1

    except Exception as e:
        print(f"⚠️  Error al procesar página {page_num}: {e}")

