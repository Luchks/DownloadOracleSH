import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.writers.CSVWriter;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class ExtractTables {
    public static void main(String[] args) throws IOException {
        String inputPdf = "documento.pdf";
        File pdfFile = new File(inputPdf);

        PDDocument document = PDDocument.load(pdfFile);
        ObjectExtractor extractor = new ObjectExtractor(document);
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

        Iterator<Page> pageIterator = extractor.extract();
        int pageNum = 1;

        while (pageIterator.hasNext()) {
            Page page = pageIterator.next();
            List<Table> tables = sea.extract(page);

            int tableNum = 1;
            for (Table table : tables) {
                String fileName = String.format("table_page_%d_table_%d.csv", pageNum, tableNum);
                try (FileWriter fw = new FileWriter(fileName);
                     PrintWriter pw = new PrintWriter(fw)) {
                    new CSVWriter().write(pw, table);
                }
                System.out.println("Tabla extraída: " + fileName);
                tableNum++;
            }
            pageNum++;
        }

        document.close();
    }
}
