package com.nlu.tai_lieu_xanh.application.user.service.impl;

import java.util.Random;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.user.dto.request.RefreshTokenRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserUpdatePasswordRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.response.LoginResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.RegisterResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserSummary;
import com.nlu.tai_lieu_xanh.application.user.dto.response.VerifyResponse;
import com.nlu.tai_lieu_xanh.application.user.mapper.UserMapper;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.domain.user.UserStatus;
import com.nlu.tai_lieu_xanh.exception.EmailExistException;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.security.CustomUserDetails;
import com.nlu.tai_lieu_xanh.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public RegisterResponse register(UserCreateRequest request) {
    var user = userMapper.toUser(request);
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new EmailExistException();
    }
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    var savedUser = userRepository.save(user);
    String token = UUID.randomUUID().toString();

    /*
     * var verificationToken = VerificationToken();
     * verificationRepository.save(verificationToken);
     * try {
     * mailService.sendVerificationEmail(user.getEmail(), token);
     * } catch (MessagingException | UnsupportedEncodingException e) {
     * throw new RuntimeException(e);
     * }
     */
    return new RegisterResponse("Registered successfully", savedUser.getEmail(), savedUser.getId());
  }

  private String generateRandomPassword(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder password = new StringBuilder();

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      password.append(characters.charAt(index));
    }

    return password.toString();
  }

  @Override
  public LoginResponse login(UserLoginRequest request) {
    var user = userRepository
        .findByEmail(request.email())
        .orElseThrow(UserNotFoundException::new);
    if (user != null && !bCryptPasswordEncoder.matches(request.password(), user.getPassword())) {
      throw new BadCredentialsException("Bad credentials");
    }
    if (user.getStatus() == UserStatus.INACTIVE) {
      throw new AccessDeniedException("Account inactive");
    }
    if (user.getStatus() == UserStatus.BAN) {
      throw new AccessDeniedException("Account ban");
    }
    String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
    String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
    var userSummary = new UserSummary(user.getId(), user.getEmail(), user.getFullName(), user.getAvatar());
    return new LoginResponse(token, refreshToken, userSummary);
  }

  @Override
  public LoginResponse refreshToken(RefreshTokenRequest request) {
    // Validate the refresh token
    if (!jwtUtil.validateToken(request.refreshToken())) {
      throw new IllegalArgumentException("Invalid or expired refresh token");
    }
    // Extract the user's email from the refresh token
    String email = jwtUtil.getUsername(request.refreshToken());
    // Retrieve the user by email
    var user = userRepository
        .findByEmail(email)
        .orElseThrow(UserNotFoundException::new);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }
    // Generate new tokens
    String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
    String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());
    var userSummary = new UserSummary(user.getId(), user.getEmail(), user.getFullName(), user.getAvatar());
    return new LoginResponse(newAccessToken, newRefreshToken, userSummary);
  }

  @Override
  public Integer getCurrentUserId() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new AccessDeniedException("User not authenticated");
    }
    var userDetails = (CustomUserDetails) auth.getPrincipal();
    return userDetails.getUser().getId();
  }

  @Override
  public VerifyResponse verifyAccount(String token) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'verifyAccount'");
  }

  @Override
  public void forgotPassword(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'forgotPassword'");
  }

  @Override
  public void updatePassword(UserUpdatePasswordRequest request) {
    Integer currentUserId = getCurrentUserId();
    var user = userRepository.findById(currentUserId).orElseThrow(UserNotFoundException::new);
    if (!bCryptPasswordEncoder.matches(request.password(), user.getPassword())) {
      throw new BadCredentialsException("Bad credentials");
    }
    String encodedPassword = bCryptPasswordEncoder.encode(request.newPassword());
    user.updatePassword(encodedPassword);
  }

}
