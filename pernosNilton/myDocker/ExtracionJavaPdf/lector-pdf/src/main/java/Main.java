import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("documento.pdf");
            PDDocument document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();

            Files.createDirectories(Paths.get("salida_csv"));

            int cuadroId = 1;

            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);

                String text = stripper.getText(document);
                String[] lines = text.split("\\r?\\n");

                List<String> tabla = new ArrayList<>();
                for (String line : lines) {
                    if (esLineaDeTabla(line)) {
                        tabla.add(line);
                    } else if (!tabla.isEmpty()) {
                        guardarComoCSV(tabla, page, cuadroId++);
                        tabla.clear();
                    }
                }

                // Si al final aún queda una tabla por guardar
                if (!tabla.isEmpty()) {
                    guardarComoCSV(tabla, page, cuadroId++);
                }
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean esLineaDeTabla(String linea) {
        // Consideramos línea de tabla si tiene al menos 3 columnas separadas por 2 o más espacios
        return linea.trim().split("\\s{2,}").length >= 3;
    }

    private static void guardarComoCSV(List<String> tabla, int pagina, int cuadro) {
        String nombreArchivo = String.format("salida_csv/pagina-%d-cuadro-%d.csv", pagina, cuadro);
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (String fila : tabla) {
                String[] columnas = fila.trim().split("\\s{2,}");
                pw.println(String.join(",", columnas));
            }
            System.out.println("Guardado: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
