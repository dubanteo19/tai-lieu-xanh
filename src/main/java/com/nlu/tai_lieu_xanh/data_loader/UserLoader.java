package com.nlu.tai_lieu_xanh.data_loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;

@Component
@Order(2)
public class UserLoader implements CommandLineRunner {
  private final AuthService authService;
  @Value("${include-data-loader}")
  boolean includeDataLoader;

  public UserLoader(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public void run(String... args) throws Exception {
    if (includeDataLoader) {
      return;
    }
    var user1 = new UserCreateRequest(
        "dubanteo2003@gmail.com", "Du Ban Teo", "123");
    var user2 = new UserCreateRequest(
        "minh@gmail.com", "Du Thanh Minh", "123");
    authService.register(user1);
    authService.register(user2);
  }
}
