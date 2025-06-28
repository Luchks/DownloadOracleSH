
package backend;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFBoxExtractor {
    public static void extractText(String pdfPath) {
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            String text = new PDFTextStripper().getText(document);
            System.out.println("Texto extraído:");
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
