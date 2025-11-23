package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.application.user.dto.response.RegisterResponse;
import com.nlu.tai_lieu_xanh.dto.request.RequestTokenReq;
import com.nlu.tai_lieu_xanh.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.dto.response.auth.LoginRes;
import com.nlu.tai_lieu_xanh.dto.response.auth.RegisterRes;
import com.nlu.tai_lieu_xanh.dto.response.auth.VerifyRes;
import com.nlu.tai_lieu_xanh.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@RequestBody UserCreateRequest request) {
    return ResponseEntity.ok(userService.register(request));
  }

  @GetMapping("/verify")
  public ResponseEntity<VerifyRes> verifyUser(@RequestParam("token") String token) {
    return ResponseEntity.ok(
        userService.verifyAccount(token));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<String> forgotPassword(@RequestParam String email) {
    userService.forgotPassword(email);
    return ResponseEntity.ok("New password sent to email");
  }

  @PostMapping("/refresh")
  public ResponseEntity<LoginRes> refresh(@RequestBody RequestTokenReq request) {
    return ResponseEntity.ok(userService.refreshToken(request));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginRes> login(@RequestBody UserLoginRequest request) {
    return ResponseEntity.ok(userService.login(request));
  }
}
