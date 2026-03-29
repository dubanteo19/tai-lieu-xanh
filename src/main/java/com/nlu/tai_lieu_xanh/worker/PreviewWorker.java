package com.nlu.tai_lieu_xanh.worker;

import com.nlu.tai_lieu_xanh.config.RabbitMQConfig;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewEvent;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewGeneratedEvent;
import com.nlu.tai_lieu_xanh.infrastructure.storage.MinioStorageService;
import com.nlu.tai_lieu_xanh.utils.ImageConverter;
import com.nlu.tai_lieu_xanh.utils.PdfPreviewGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PreviewWorker {
  private final MinioStorageService minioStorageService;
  private final RabbitMessagingTemplate rabbitMessagingTemplate;
  public static final String BUCKET = "previews";

  @RabbitListener(queues = RabbitMQConfig.PREVIEW_CREATE_QUEUE)
  public void handleCreatePreviewMessage(PreviewEvent event) {
    log.info("I've received the message, process it", event.toString());
    try (var pdfstream = minioStorageService.downLoadFile(event.filePath())) {
      var previews = PdfPreviewGenerator.generate(pdfstream, event.numPages());
      int generatedpagecounts = 0;
      for (int i = 0; i < previews.size(); i++) {
        var baos = new ByteArrayOutputStream();
        ImageConverter.convertToWebp(previews.get(i), baos);
        int pageIndex = i + 1;
        String objectname = "doc-" + event.MDocId() + "/page-" + pageIndex + ".webp";
        minioStorageService.uploadFile(BUCKET, objectname, baos, ImageConverter.WEBP_CONTENT_TYPE);
        generatedpagecounts++;
        log.info("Upload preview image successfully");
      }
      String prefix = "doc-" + event.MDocId();
      var previewEvent = new PreviewGeneratedEvent(event.MDocId(), prefix, generatedpagecounts);
      rabbitMessagingTemplate.convertAndSend(
          RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.PREVIEW_GENERATED_ROUTING_KEY, previewEvent);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
