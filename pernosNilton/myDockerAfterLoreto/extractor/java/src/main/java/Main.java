import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.writers.CSVWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        File pdf = new File("pdfs/archivo.pdf");
        FileInputStream fis = new FileInputStream(pdf);
        PDDocument document = PDDocument.load(fis);

        ObjectExtractor extractor = new ObjectExtractor(document);
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        CSVWriter writer = new CSVWriter();

        int pageCount = document.getNumberOfPages();
        for (int pageNumber = 1; pageNumber <= pageCount; pageNumber++) {
            Page page = extractor.extract(pageNumber);
            List<Table> tables = sea.extract(page);

            int tableIndex = 1;
            for (Table table : tables) {
                String filename = String.format("pdfs/tabla_p%d_%d.csv", pageNumber, tableIndex++);
                try (PrintWriter pw = new PrintWriter(filename)) {
                    writer.write(table, pw);
                }
                System.out.println("Exportado: " + filename);
            }
        }

        document.close();
        fis.close();
    }
}
