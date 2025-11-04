package com.nlu.tai_lieu_xanh.utils;

import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;

public class PageExtractor {

  public static int extractPageCount(MultipartFile file) throws Exception {
    String fileType = file.getContentType();
    if ("application/pdf".equals(fileType)) {
      return getPdfPageCount(file);
    } else {
      throw new IllegalArgumentException("Unsupported file type: " + fileType);
    }
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
