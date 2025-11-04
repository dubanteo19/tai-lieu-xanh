package com.nlu.tai_lieu_xanh.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfPreviewGenerator {
  public static final int DEFAULT_DPI = 100;

  public static List<BufferedImage> generate(InputStream pdfStream, int numPages) throws IOException {
    List<BufferedImage> previews = new ArrayList<>();
    try (var document = PDDocument.load(pdfStream)) {
      var reader = new PDFRenderer(document);
      for (int i = 0; i < numPages; i++) {
        var image = reader.renderImageWithDPI(i, DEFAULT_DPI);
        previews.add(image);
      }
    }
    return previews;
  }
}
