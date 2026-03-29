package com.nlu.tai_lieu_xanh.application.mdoc.service.impl;

import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.PresignedUrlRes;
import com.nlu.tai_lieu_xanh.application.mdoc.service.MDocService;
import com.nlu.tai_lieu_xanh.config.RabbitMQConfig;
import com.nlu.tai_lieu_xanh.domain.mdoc.FileType;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDocRepository;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewGeneratedEvent;
import com.nlu.tai_lieu_xanh.infrastructure.storage.MinioStorageService;
import com.nlu.tai_lieu_xanh.utils.PageExtractor;
import com.nlu.tai_lieu_xanh.utils.UrlPreviewer;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Log4j2
public class MDocServiceImpl implements MDocService {

  private final MDocRepository mDocRepository;
  private final MinioStorageService minioStorageService;

  @Override
  @RabbitListener(queues = RabbitMQConfig.PREVIEW_GENERATED_QUEUE)
  @Transactional
  public void handlePreivewGeneratedEvent(PreviewGeneratedEvent event) {
    var mdoc = findById(event.mDocId());

    mdoc.setPreviewCount(event.numPages());
    log.info("update mdoc preview count successfully");
  }

  @Override
  public MDoc findById(Long id) {
    return mDocRepository.findById(id).orElseThrow(() -> new RuntimeException());
  }

  @Override
  @Transactional
  public MDoc uploadDocument(MultipartFile file, Long authorId) {
    String fileName = file.getOriginalFilename();
    String objectName = minioStorageService.uploadFile(authorId, file);
    var extension = fileName.substring(fileName.lastIndexOf("."));
    var fileType = extension.equalsIgnoreCase(".pdf") ? FileType.PDF : FileType.DOCX;
    long fileSize = file.getSize(); // In bytes
    int pages;
    pages = PageExtractor.extractPageCount(file);
    var mDoc = MDoc.create(fileName, objectName, fileSize, pages, fileType);
    log.info("upload document successfully");
    return mDocRepository.save(mDoc);
  }

  @Override
  public List<String> getPreivewUrls(Long id) {
    var mdoc = findById(id);
    int previewCount = mdoc.getPreviewCount();
    return UrlPreviewer.generate(mdoc.getId(), previewCount);
  }

  @Override
  public PresignedUrlRes download(Long id) {
    return null;
  }
  ;
}
