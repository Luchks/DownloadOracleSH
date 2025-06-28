import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.*;
import java.util.List;

public class PdfTableExtractor {
    public static void main(String[] args) throws IOException {
        File pdfFile = new File("documento.pdf");
        if (!pdfFile.exists()) {
            System.err.println("❌ El archivo 'documento.pdf' no fue encontrado.");
            return;
        }

        new File("output").mkdirs();

        PDDocument document = PDDocument.load(pdfFile);
        ObjectExtractor extractor = new ObjectExtractor(document);
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();

        int pageIndex = 1;
        for (Page page : extractor.extract()) {
            List<Table> tables = sea.extract(page);
            if (tables.isEmpty()) {
                tables = bea.extract(page);
            }

            int tableIndex = 1;
            for (Table table : tables) {
                String filename = String.format("output/page_%02d_table_%02d.csv", pageIndex, tableIndex);
                try (PrintWriter writer = new PrintWriter(new File(filename))) {
                    writer.print(table.toString());
                    System.out.println("✅ Guardado: " + filename);
                }
                tableIndex++;
            }
            pageIndex++;
        }

        document.close();
    }
}
