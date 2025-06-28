package com.example.pdfextractor;

import java.io.File;
import java.util.List;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

public class TabulaExtractor {
    public static class TableData {
        public String[][] data;
        public TableData(String[][] data) {
            this.data = data;
        }
    }

    public static TableData extractFirstTable(String pdfPath) throws Exception {
        ObjectExtractor extractor = new ObjectExtractor(PDDocument.load(new File(pdfPath)));
        Page page = extractor.extract().next();
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        List<Table> tables = sea.extract(page);

        if (tables.isEmpty()) throw new Exception("No se encontró ninguna tabla");

        Table table = tables.get(0);
        List<List<RectangularTextContainer>> rows = table.getRows();

        String[][] result = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            List<RectangularTextContainer> cells = rows.get(i);
            result[i] = cells.stream().map(RectangularTextContainer::getText).toArray(String[]::new);
        }

        return new TableData(result);
    }
}

