package com.nlu.tai_lieu_xanh.producer;

import com.nlu.tai_lieu_xanh.config.RabbitMQConfig;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreviewMessageProducer {
  private final RabbitTemplate rabbitTemplate;

  public void sendCreatePreviewTask(PreviewEvent event) {
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "document.preview", event);
  }
}
