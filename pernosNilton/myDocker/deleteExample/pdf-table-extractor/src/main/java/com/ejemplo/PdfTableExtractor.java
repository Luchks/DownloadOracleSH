package com.ejemplo;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import technology.tabula.*;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.writers.CSVWriter;

public class PdfTableExtractor {
    public static void main(String[] args) throws Exception {
        File pdfFile = new File("ejemplo.pdf");

        // Asegúrate de que exista la carpeta output
        new File("output").mkdirs();

        ObjectExtractor oe = new ObjectExtractor(PDDocument.load(pdfFile));
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();

        CSVWriter writer = new CSVWriter();
        int tableIndex = 0;

        for (int i = 1; i <= oe.getPageCount(); i++) {
            Page page = oe.extract(i);

            float pageWidth = (float) page.getWidth();
            float pageHeight = (float) page.getHeight();

            int rows = 4;
            int cols = 2;

            float cellWidth = pageWidth / cols;
            float cellHeight = pageHeight / rows;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    float x = col * cellWidth;
                    float y = row * cellHeight;

                    // Usar tecnología.tabula.Rectangle, no java.awt.Rectangle
                    technology.tabula.Rectangle area = new technology.tabula.Rectangle(x, y, cellWidth, cellHeight);
                    Page areaPage = page.getArea(area);

                    ExtractionAlgorithm algorithm = sea.isTabular(areaPage) ? sea : bea;
                    List<? extends Table> tables = algorithm.extract(areaPage);

                    for (Table table : tables) {
                        List<List<RectangularTextContainer>> rowsData = table.getRows();
                        if (!rowsData.isEmpty()) {
                            String fileName = "output/tabla_pag" + i + "_r" + row + "_c" + col + "_t" + tableIndex + ".csv";
                            try (FileWriter out = new FileWriter(fileName)) {
                                writer.write(out, table);
                            }
                            System.out.println("Guardado: " + fileName);
                            tableIndex++;
                        }
                    }
                }
            }
        }

        oe.close();
    }
}
