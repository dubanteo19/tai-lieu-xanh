package com.nlu.tai_lieu_xanh.interfaces.rest.user;

import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.response.LoginResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.RegisterResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserSummary;
import com.nlu.tai_lieu_xanh.application.user.dto.response.VerifyResponse;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Log4j2
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@RequestBody UserCreateRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody UserLoginRequest request, HttpServletResponse response) {
    var tokens = authService.login(request);
    String refreshToken = tokens.refreshToken();
    setRefreshTokenCookie(response, refreshToken);
    var responseBody = new LoginResponse(tokens.accessToken());
    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/verify")
  public ResponseEntity<VerifyResponse> verifyUser(@RequestParam("token") String token) {
    return ResponseEntity.ok(authService.verifyAccount(token));
  }

  @PostMapping("/refresh")
  public ResponseEntity<LoginResponse> refresh(
      HttpServletRequest request, HttpServletResponse response) {
    log.warn("user's trying to refresh");
    String refreshToken = extractRefreshTokenFromCookie(request);
    var authTokens = authService.refreshToken(refreshToken);
    setRefreshTokenCookie(response, authTokens.refreshToken());
    var responseBody = new LoginResponse(authTokens.accessToken());
    return ResponseEntity.ok(responseBody);
  }

  private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
    var cookie = new Cookie("refreshToken", refreshToken);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/api/v1/auth");
    cookie.setMaxAge((int) Duration.ofDays(7).toSeconds());
    response.addCookie(cookie);
  }

  private String extractRefreshTokenFromCookie(HttpServletRequest request) {
    if (request.getCookies() == null) {
      throw new RuntimeException("No cookies found");
    }
    return Arrays.stream(request.getCookies())
        .filter(c -> "refreshToken".equals(c.getName()))
        .findFirst()
        .map(Cookie::getValue)
        .orElseThrow(() -> new AccessDeniedException("Refresh token missing"));
  }

  @GetMapping("/me")
  public ResponseEntity<UserSummary> me() {
    return ResponseEntity.ok(authService.me());
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("refreshToken", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/api/v1/auth");
    cookie.setMaxAge(0); // delete cookie

    response.addCookie(cookie);

    return ResponseEntity.ok().build();
  }
}
