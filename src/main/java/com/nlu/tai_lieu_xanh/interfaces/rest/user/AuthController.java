package com.nlu.tai_lieu_xanh.interfaces.rest.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.user.dto.request.RefreshTokenRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.response.LoginResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.RegisterResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.VerifyResponse;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@RequestBody UserCreateRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
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

}
