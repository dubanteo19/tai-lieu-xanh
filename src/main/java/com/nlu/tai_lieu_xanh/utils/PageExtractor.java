package com.nlu.tai_lieu_xanh.utils;

import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;

public class PageExtractor {

  public static int extractPageCount(MultipartFile file) {
    String fileType = file.getContentType();
    if ("application/pdf".equals(fileType)) {
      try {
        return getPdfPageCount(file);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      throw new IllegalArgumentException("Unsupported file type: " + fileType);
    }
    return 0;
  }

  private static int getPdfPageCount(MultipartFile file) throws Exception {
    try (InputStream inputStream = file.getInputStream();) {
      PDDocument document = PDDocument.load(inputStream);
      var pageCount = document.getNumberOfPages();
      document.close();
      return pageCount;
    }

  }
}
