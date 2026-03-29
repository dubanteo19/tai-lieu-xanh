package com.nlu.tai_lieu_xanh.config;

import java.util.List;

/** SecurityConstant */
public class SecurityConstant {

  public static final List<String> PUBLIC_ENDPOINTS =
      List.of(
          "/auth/login",
          "/auth/refresh",
          "/auth/register",
          "/sse/**",
          "/majors/**",
          "/documents/**",
          "/posts/**",
          "/tags/**",
          "/reports/**");
}
