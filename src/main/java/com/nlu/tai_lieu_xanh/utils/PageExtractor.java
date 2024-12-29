package com.nlu.tai_lieu_xanh.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class PageExtractor {

    public static int extractPageCount(MultipartFile file) throws Exception {
        String fileType = file.getContentType();

        if ("application/pdf".equals(fileType)) {
            return getPdfPageCount(file);
        } else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(fileType)) {
            return getDocxPageCount(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }

    private static int getPdfPageCount(MultipartFile file) throws Exception {

        try (InputStream inputStream = file.getInputStream();
        ) {
            PDDocument document = PDDocument.load(inputStream);
            var pageCount = document.getNumberOfPages();
            document.close();
            return pageCount;
        }
    }

    private static int getDocxPageCount(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream(); XWPFDocument docxDocument = new XWPFDocument(inputStream)) {
            return docxDocument.getProperties().getExtendedProperties().getPages();
        }
    }
}
