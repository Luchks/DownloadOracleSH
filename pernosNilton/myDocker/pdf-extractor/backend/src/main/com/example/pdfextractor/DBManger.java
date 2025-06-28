package com.example.pdfextractor;

import java.sql.*;
import com.mongodb.client.*;
import org.bson.Document;

public class DBManager {
    public static void saveToPostgres(TabulaExtractor.TableData table) throws Exception {
        String url = System.getenv("POSTGRES_URL");
        String user = System.getenv("POSTGRES_USER");
        String pass = System.getenv("POSTGRES_PASSWORD");

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS pdf_table (id SERIAL PRIMARY KEY, col1 TEXT, col2 TEXT, col3 TEXT)");
            }

            String sql = "INSERT INTO pdf_table (col1, col2, col3) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (String[] row : table.data) {
                    ps.setString(1, row.length > 0 ? row[0] : null);
                    ps.setString(2, row.length > 1 ? row[1] : null);
                    ps.setString(3, row.length > 2 ? row[2] : null);
                    ps.executeUpdate();
                }
            }
        }
    }

    public static void saveToMongo(TabulaExtractor.TableData table) {
        String mongoUri = System.getenv("MONGO_URL");
        try (MongoClient client = MongoClients.create(mongoUri)) {
            MongoDatabase db = client.getDatabase("pdfdata");
            MongoCollection<Document> col = db.getCollection("pdf_table");

            for (String[] row : table.data) {
                Document doc = new Document();
                if (row.length > 0) doc.append("col1", row[0]);
                if (row.length > 1) doc.append("col2", row[1]);
                if (row.length > 2) doc.append("col3", row[2]);
                col.insertOne(doc);
            }
        }
    }
}
