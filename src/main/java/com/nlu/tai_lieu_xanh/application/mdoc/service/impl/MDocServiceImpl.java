package com.nlu.tai_lieu_xanh.application.mdoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.PresignedUrlRes;
import com.nlu.tai_lieu_xanh.application.mdoc.service.MDocService;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import com.nlu.tai_lieu_xanh.config.RabbitMQConfig;
import com.nlu.tai_lieu_xanh.domain.mdoc.FileType;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDocRepository;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewGeneratedEvent;
import com.nlu.tai_lieu_xanh.infrastructure.storage.MinioStorageService;
import com.nlu.tai_lieu_xanh.utils.PageExtractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MDocServiceImpl implements MDocService {

  private final MDocRepository mDocRepository;
  private final MinioStorageService minioStorageService;
  private final AuthService authService;

  @Override

  @RabbitListener(queues = RabbitMQConfig.PREVIEW_GENERATED_QUEUE)
  public void handlePreivewGeneratedEvent(PreviewGeneratedEvent event) {
    var mdoc = findById(event.mDocId());
    mdoc.setPreviewCount(event.numPages());
    log.info("update mdoc preview count successfully");
  }

  @Override
  public MDoc findById(Long id) {
    return mDocRepository.findById(id)
        .orElseThrow(() -> new RuntimeException());
  }

  @Override
  public MDoc uploadDocument(MultipartFile file) {
    Long currentUserId = authService.getCurrentUserId();
    String fileName = file.getOriginalFilename();
    String urlPath = minioStorageService.uploadFile(currentUserId, file);
    var extension = fileName.substring(fileName.lastIndexOf("."));
    var fileType = extension.equalsIgnoreCase(".pdf") ? FileType.PDF : FileType.DOCX;
    long fileSize = file.getSize(); // In bytes
    int pages;
    pages = PageExtractor.extractPageCount(file);
    var mDoc = MDoc.create(fileName, fileSize, pages, fileType);
    return mDocRepository.save(mDoc);
  }

  @Override
  public List<String> getPreivewUrls(Long id) {
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

  @Override
  public PresignedUrlRes download(Long id) {
    return null;
  };

}
