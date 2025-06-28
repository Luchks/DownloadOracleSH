package com.example.pdfextractor;

import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import com.mongodb.client.*;
import org.bson.Document;

import java.io.*;
import java.sql.*;
import java.util.List;

public class PDFTableExtractor {

    public void extractAndStore(String pdfPath) {
        try {
            File pdfFile = new File(pdfPath);
            PDDocument pd = PDDocument.load(pdfFile);
            ObjectExtractor oe = new ObjectExtractor(pd);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

            MongoClient mongoClient = MongoClients.create(System.getenv("MONGO_URI"));
            MongoDatabase mongoDb = mongoClient.getDatabase("pdfdata");
            MongoCollection<Document> mongoCol = mongoDb.getCollection("tables");

            Connection postgresConn = DriverManager.getConnection(System.getenv("POSTGRES_URI"), "user", "pass");
            Statement stmt = postgresConn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS table_data (id SERIAL PRIMARY KEY, row TEXT)");

            for (int pageNum = 1; pageNum <= pd.getNumberOfPages(); pageNum++) {
                Page page = oe.extract(pageNum);
                Table table = sea.extract(page).get(0);

                for (List<RectangularTextContainer> row : table.getRows()) {
                    StringBuilder rowData = new StringBuilder();
                    for (RectangularTextContainer cell : row) {
                        rowData.append(cell.getText()).append(" | ");
                    }
                    String rowText = rowData.toString();

                    mongoCol.insertOne(new Document("page", pageNum).append("row", rowText));
                    stmt.executeUpdate("INSERT INTO table_data (row) VALUES ('" + rowText.replace("'", "''") + "')");
                }
            }

            pd.close();
            postgresConn.close();
            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
