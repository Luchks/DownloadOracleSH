
package backend;

import java.io.*;

public class TabulaExtractor {
    public static void extractTables(String pdfPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "tabula/tabula.jar", "-p", "all", "-f", "CSV", pdfPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("Tablas extraídas:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
