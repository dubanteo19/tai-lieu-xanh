package com.nlu.tai_lieu_xanh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.response.LoginResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.RegisterResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.VerifyResponse;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import com.nlu.tai_lieu_xanh.application.user.service.UserService;
import com.nlu.tai_lieu_xanh.domain.user.dto.request.RequestTokenReq;
import com.nlu.tai_lieu_xanh.dto.response.auth.LoginRes;
import com.nlu.tai_lieu_xanh.dto.response.auth.VerifyRes;

@RequestMapping("/auth")
@RestController
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@RequestBody UserCreateRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @GetMapping("/verify")
  public ResponseEntity<VerifyResponse> verifyUser(@RequestParam("token") String token) {
    return ResponseEntity.ok(
        authService.verifyAccount(token));
  }

  @PostMapping("/refresh")
  public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(authService.refreshToken(request));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginRes> login(@RequestBody UserLoginRequest request) {
    return ResponseEntity.ok(userService.login(request));
  }
}
