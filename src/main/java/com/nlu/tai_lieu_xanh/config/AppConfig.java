package com.nlu.tai_lieu_xanh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {
  @Bean
  BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder(12);
  }

}
