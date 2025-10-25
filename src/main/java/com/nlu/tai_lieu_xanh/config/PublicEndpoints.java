package com.nlu.tai_lieu_xanh.config;

import java.util.List;

public class PublicEndpoints {
  public static final List<String> ENDPOINTS = List.of(
      "/auth/**",
      "/majors/**",
      "/posts/**",
      "/tags/**");
}
