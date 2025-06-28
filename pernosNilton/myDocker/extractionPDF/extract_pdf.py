import pdfplumber
import os
import csv

pdf_dir = "pdfs"
output_dir = "outputs"
os.makedirs(output_dir, exist_ok=True)

for filename in os.listdir(pdf_dir):
    if filename.endswith(".pdf"):
        pdf_path = os.path.join(pdf_dir, filename)
        base_name = os.path.splitext(filename)[0]  # sin extensión

        with pdfplumber.open(pdf_path) as pdf:
            for page_num, page in enumerate(pdf.pages, start=1):
                tables = page.extract_tables()
                
                if not tables:
                    print(f"[!] Sin tablas en {base_name} página {page_num}")
                    continue
                
                for table_num, table in enumerate(tables, start=1):
                    output_file = os.path.join(
                        output_dir,
                        f"{base_name}_pagina_{page_num}_tabla_{table_num}.csv"
                    )
                    with open(output_file, "w", newline="", encoding="utf-8") as csvfile:
                        writer = csv.writer(csvfile)
                        for row in table:
                            writer.writerow(row)
                    print(f"[✓] Guardado: {output_file}")

