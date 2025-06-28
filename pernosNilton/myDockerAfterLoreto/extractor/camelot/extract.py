import camelot

print("Usando backend:", camelot.__version__, "-", camelot.read_pdf.__defaults__)

tables = camelot.read_pdf("pdfs/archivo.pdf", pages='all')

for i, table in enumerate(tables):
    print(f"\n--- Tabla {i+1} ---")
    print(table.df)
    table.to_csv(f"pdfs/tabla_{i+1}.csv")
