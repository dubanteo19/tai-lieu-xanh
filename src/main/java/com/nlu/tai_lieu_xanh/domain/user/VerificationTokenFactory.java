package com.nlu.tai_lieu_xanh.domain.user;

import java.util.UUID;

public class VerificationTokenFactory {
  public static VerificationToken create(User user) {
    String token = UUID.randomUUID().toString();
    return new VerificationToken(user, token);
  }
}
