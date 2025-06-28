package com.example.pdfextractor;

public class App {
    public static void main(String[] args) throws Exception {
        String pdfPath = "/app/sample.pdf";

        String text = PDFBoxExtractor.extractText(pdfPath);
        System.out.println("Texto extraído:\n" + text);

        TabulaExtractor.TableData table = TabulaExtractor.extractFirstTable(pdfPath);

        DBManager.saveToPostgres(table);
        DBManager.saveToMongo(table);

        System.out.println("✅ Datos guardados en PostgreSQL y MongoDB.");
    }
}
