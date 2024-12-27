package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.response.post.MDocRes;
import com.nlu.tai_lieu_xanh.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.model.FileType;
import com.nlu.tai_lieu_xanh.model.MDoc;
import com.nlu.tai_lieu_xanh.repository.MDocRepository;
import com.nlu.tai_lieu_xanh.utils.PageExtractor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MDocService {
    MDocRepository mDocRepository;
    PostMapper postMapper = PostMapper.INSTANCE;
    FtpService ftpService;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public Pair<MDoc, String> uploadTemp(Integer useId, MultipartFile file) throws IOException {
        var fileName = file.getOriginalFilename();
        var path = "";
        try {
            path = ftpService.uploadTempFile(file.getInputStream(), fileName, useId);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
//                thumbnail = generateDocxThumbnail(mDoc.getUrl());
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
