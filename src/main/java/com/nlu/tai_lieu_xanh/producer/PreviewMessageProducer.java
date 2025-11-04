package com.nlu.tai_lieu_xanh.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.config.RabbitMQConfig;
import com.nlu.tai_lieu_xanh.dto.request.m.doc.PreviewEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreviewMessageProducer {
  private final RabbitTemplate rabbitTemplate;

  public void sendCreatePreviewTask(PreviewEvent event) {
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.EXCHANGE_NAME,
        RabbitMQConfig.PREVIEW_CREATE_ROUTING_KEY,
        event);
  }
}
