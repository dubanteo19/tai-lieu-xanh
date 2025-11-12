package com.nlu.tai_lieu_xanh.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.config.RabbitMQConfig;
import com.nlu.tai_lieu_xanh.dto.request.m.doc.PreviewGeneratedEvent;
import com.nlu.tai_lieu_xanh.model.FileType;
import com.nlu.tai_lieu_xanh.model.MDoc;
import com.nlu.tai_lieu_xanh.repository.MDocRepository;
import com.nlu.tai_lieu_xanh.utils.PageExtractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MDocService {
  private final MDocRepository mDocRepository;
  private final MinioStorageService minioStorageService;

  @RabbitListener(queues = RabbitMQConfig.PREVIEW_GENERATED_QUEUE)
  public void handlePreivewGeneratedEvent(PreviewGeneratedEvent event) {
    var mdoc = findById(event.mDocId());
    mdoc.setPreviewCount(event.mDocId());
    mDocRepository.save(mdoc);
    log.info("update mdoc preview count successfully");
  }

  public MDoc findById(Integer id) {
    return mDocRepository.findById(id)
        .orElseThrow(() -> new RuntimeException());
  }

  public MDoc uploadTemp(Integer userId, MultipartFile file) {
    String fileName = file.getOriginalFilename();
    String urlPath = minioStorageService.uploadFile(userId, file);
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
    mDoc.setUrl(urlPath);
    mDoc.setDownloads(0);
    mDoc.setFileName(fileName);
    mDoc.setFileSize(fileSize);
    mDoc.setPages(pages);
    mDoc.setFileType(fileType);
    return mDocRepository.save(mDoc);
  }

  public List<String> getPreivewUrls(int id) {
    List<String> previewUrls = new ArrayList<>();
    var mdoc = findById(id);
    int previewCount = mdoc.getPreviewCount();
    String HOST = "http://localhost";
    String PORT = "9000";
    String prefix = HOST + ":" + PORT + "/previews/doc-" + id + "/page-";
    for (int i = 0; i < previewCount; i++) {
      String url = prefix + (i + 1) + ".webp";
      previewUrls.add(url);
    }
    return previewUrls;
  }

}
