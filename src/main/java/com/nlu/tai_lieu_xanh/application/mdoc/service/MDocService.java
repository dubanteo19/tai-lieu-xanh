package com.nlu.tai_lieu_xanh.application.mdoc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.PresignedUrlRes;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewGeneratedEvent;

public interface MDocService {
  void handlePreivewGeneratedEvent(PreviewGeneratedEvent event);

  MDoc findById(Long id);

  MDoc uploadDocument(MultipartFile file);

  List<String> getPreivewUrls(Long id);

  PresignedUrlRes download(Long id);
}
