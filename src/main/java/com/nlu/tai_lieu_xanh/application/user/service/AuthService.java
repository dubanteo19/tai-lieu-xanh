package com.nlu.tai_lieu_xanh.application.user.service;

import com.nlu.tai_lieu_xanh.application.user.dto.request.RefreshTokenRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserUpdatePasswordRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.response.LoginResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.RegisterResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.VerifyResponse;

public interface AuthService {
  RegisterResponse register(UserCreateRequest request);

  VerifyResponse verifyAccount(String token);

  LoginResponse login(UserLoginRequest request);

  void updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest);

  LoginResponse refreshToken(RefreshTokenRequest request);

  void forgotPassword(String email);

  Integer getCurrentUserId();
}
