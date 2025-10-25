package com.nlu.tai_lieu_xanh.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.antlr.v4.runtime.misc.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.model.FileType;
import com.nlu.tai_lieu_xanh.model.MDoc;
import com.nlu.tai_lieu_xanh.repository.MDocRepository;
import com.nlu.tai_lieu_xanh.utils.PageExtractor;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MDocService {
  private final MDocRepository mDocRepository;
  private final FtpService ftpService;
  private final MinioStorageService minioStorageService;

  public Pair<MDoc, String> uploadTemp(Integer useId, MultipartFile file) throws IOException {
    var fileName = file.getOriginalFilename();
    var path = "";
    try {
      minioStorageService.uploadFile(fileName, file);
    } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
        | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
      e.printStackTrace();
    }
    var extension = fileName.substring(fileName.lastIndexOf("."));
    var fileType = extension.equalsIgnoreCase(".pdf") ? FileType.PDF : FileType.DOCX;
    long fileSize = file.getSize(); // In bytes
    int pages;
    try {
      pages = PageExtractor.extractPageCount(file);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    MDoc mDoc = new MDoc();
    mDoc.setUrl(path);
    mDoc.setDownloads(0);
    mDoc.setFileName(fileName);
    mDoc.setFileSize(fileSize);
    mDoc.setPages(pages);
    mDoc.setFileType(fileType);
    String thumb = generateThumbnail(mDoc, useId, file.getInputStream());
    return new Pair<>(mDocRepository.save(mDoc), thumb);
  }

  private BufferedImage generatePdfThumbnail(InputStream inputStream) throws IOException {
    try (PDDocument document = PDDocument.load(inputStream)) {

      PDFRenderer renderer = new PDFRenderer(document);
      return renderer.renderImageWithDPI(0, 150); // Render first page at 150 DPI
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      inputStream.close();
    }
  }

  private String generateThumbnail(MDoc mDoc, Integer userId, InputStream inputStream) {
    try {
      BufferedImage thumbnail = null;
      if (mDoc.getFileType() == FileType.PDF) {
        thumbnail = generatePdfThumbnail(inputStream);
      } else {
        // thumbnail = generateDocxThumbnail(mDoc.getUrl());
      }
      System.out.println(mDoc);
      String thumbPath = mDoc.getFileName() + "-thumb.png";
      System.out.println(thumbPath);
      File tempThumbFile = File.createTempFile("thumb", ".png");
      ImageIO.write(thumbnail, "PNG", tempThumbFile);
      try (var fileInputStream = new FileInputStream(tempThumbFile)) {
        return ftpService.uploadTempFile(fileInputStream, thumbPath, userId);
      } finally {
        tempThumbFile.delete(); // Cleanup temporary file
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
