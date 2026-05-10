package com.nlu.tai_lieu_xanh.application.mdoc.service;

import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.PresignedUrlResponse;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewGeneratedEvent;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MDocService {
  void handlePreivewGeneratedEvent(PreviewGeneratedEvent event);

  MDoc findById(Long id);

  MDoc uploadDocument(MultipartFile file, Long authorId);

  List<String> getPreivewUrls(Long id);

  PresignedUrlResponse download(Long id);
}
