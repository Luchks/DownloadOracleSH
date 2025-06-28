
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader {
    public static void main(String[] args) {
        try {
            File file = new File("documento.pdf");
            PDDocument document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println("Texto del PDF:\n" + text);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
