package com.example.pdfextractor;

public class Main {
    public static void main(String[] args) {
        PDFTableExtractor extractor = new PDFTableExtractor();
        extractor.extractAndStore("/data/pdfs/sample.pdf");
    }
}
