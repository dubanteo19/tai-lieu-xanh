package com.nlu.tai_lieu_xanh.application.user.service;

import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserUpdatePasswordReq;
import com.nlu.tai_lieu_xanh.application.user.dto.response.LoginRes;
import com.nlu.tai_lieu_xanh.application.user.dto.response.RegisterRes;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserInfoRes;
import com.nlu.tai_lieu_xanh.application.user.dto.response.VerifyRes;
import com.nlu.tai_lieu_xanh.dto.request.RequestTokenReq;

public interface AuthService {
  RegisterRes register(UserCreateRequest request);

  VerifyRes verifyAccount(String token);

  LoginRes login(UserLoginRequest request);

  UserInfoRes updatePassword(Integer id, UserUpdatePasswordReq userUpdatePasswordReq);

  LoginRes refreshToken(RequestTokenReq request);

  void forgotPassword(String email);
}
