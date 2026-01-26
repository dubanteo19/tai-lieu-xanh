package com.nlu.tai_lieu_xanh.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

//@Configuration
//@EnableRabbit
public class RabbitMQConfig {
  public static final String EXCHANGE_NAME = "document.exchange";
  // Request queue (from document → worker)
  public static final String PREVIEW_CREATE_QUEUE = "document.preview.queue";
  public static final String PREVIEW_CREATE_ROUTING_KEY = "document.preview";

  // Response queue (from worker → document)
  public static final String PREVIEW_GENERATED_QUEUE = "document.preview.generated.queue";
  public static final String PREVIEW_GENERATED_ROUTING_KEY = "document.preview.generated";

  @Bean
  Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jackson2JsonMessageConverter());
    return template;
  }
}
