package com.nlu.tai_lieu_xanh.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
  public static final String EXCHANGE_NAME = "document.exchange";
  // Request queue (from document → worker)
  public static final String PREVIEW_CREATE_QUEUE = "document.preview.queue";
  public static final String PREVIEW_CREATE_ROUTING_KEY = "document.preview";

  // Response queue (from worker → document)
  public static final String PREVIEW_GENERATED_QUEUE = "document.preview.generated.queue";
  public static final String PREVIEW_GENERATED_ROUTING_KEY = "document.preview.generated";

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  Queue previewCreateQueue() {
    return new Queue(PREVIEW_CREATE_QUEUE);
  }

  @Bean
  Queue previewGeneratedQueue() {
    return new Queue(PREVIEW_GENERATED_QUEUE);
  }

  @Bean
  Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  Binding previewCreateBinding() {
    return BindingBuilder
        .bind(previewCreateQueue())
        .to(exchange())
        .with(PREVIEW_CREATE_ROUTING_KEY);
  }

  @Bean
  Binding previewGenerateBinding() {
    return BindingBuilder
        .bind(previewGeneratedQueue())
        .to(exchange())
        .with(PREVIEW_GENERATED_ROUTING_KEY);
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jackson2JsonMessageConverter());
    return template;
  }
}
