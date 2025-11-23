package com.nlu.tai_lieu_xanh.data_loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)

public class PostLoader implements CommandLineRunner {
  @Value("${include-data-loader}")
  boolean includeDataLoader;

  @Override
  public void run(String... args) throws Exception {
    if (includeDataLoader) {
      return;
    }
  }
}
