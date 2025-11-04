package com.nlu.tai_lieu_xanh.worker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.config.RabbitMQConfig;
import com.nlu.tai_lieu_xanh.dto.request.m.doc.PreviewEvent;
import com.nlu.tai_lieu_xanh.dto.request.m.doc.PreviewGeneratedEvent;
import com.nlu.tai_lieu_xanh.service.MinioStorageService;
import com.nlu.tai_lieu_xanh.utils.ImageConverter;
import com.nlu.tai_lieu_xanh.utils.PdfPreviewGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class PreviewWorker {
  private final MinioStorageService minioStorageService;
  private final RabbitMessagingTemplate rabbitMessagingTemplate;
  public static final String BUCKET = "previews";

  @RabbitListener(queues = RabbitMQConfig.PREVIEW_CREATE_QUEUE)
  public void handleCreatePreviewMessage(PreviewEvent event) {
    log.info("I receive the message and I am about to process it");
    try (var pdfStream = minioStorageService.downLoadFile(event.filePath())) {
      var previews = PdfPreviewGenerator.generate(pdfStream, event.numPages());
      int generatedPageCounts = 0;
      for (int i = 0; i < previews.size(); i++) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageConverter.convertToWebp(previews.get(i), baos);
        String objectName = "doc-" + event.MDocId() + "/page-" + (i + 1) + ".webp";
        minioStorageService.uploadFile(BUCKET, objectName, baos, ImageConverter.WEBP_CONTENT_TYPE);
        generatedPageCounts++;
        log.info("upload preview image successfully");
      }
      String prefix = "doc-" + event.MDocId();
      var previewEvent = new PreviewGeneratedEvent(event.MDocId(), prefix, generatedPageCounts);
      rabbitMessagingTemplate.convertAndSend(
          RabbitMQConfig.EXCHANGE_NAME,
          RabbitMQConfig.PREVIEW_GENERATED_ROUTING_KEY,
          previewEvent);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
