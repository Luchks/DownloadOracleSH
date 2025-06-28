
package backend;

public class App {
    public static void main(String[] args) {
        System.out.println("PDF Extractor started.");
        PDFBoxExtractor.extractText("sample.pdf");
        TabulaExtractor.extractTables("sample.pdf");
        DBManager.saveToPostgres("Texto extraído de PDF");
        DBManager.saveToMongo("Tablas extraídas del PDF");
    }
}
