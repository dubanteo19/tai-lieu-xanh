package com.nlu.tai_lieu_xanh.application.user.service.impl;

import java.util.Random;
import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.request.UserUpdatePasswordReq;
import com.nlu.tai_lieu_xanh.application.user.dto.response.RegisterResponse;
import com.nlu.tai_lieu_xanh.application.user.mapper.UserMapper;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.domain.user.UserStatus;
import com.nlu.tai_lieu_xanh.exception.EmailExistException;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.repository.VerificationRepository;
import com.nlu.tai_lieu_xanh.service.MailService;
import com.nlu.tai_lieu_xanh.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final VerificationRepository verificationRepository;
  private final JwtUtil jwtUtil;
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final MailService mailService;

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
  public VerifyResponse verifyAccount(String token) {
    /*
     * var verificationToken = verificationRepository.findByToken((token))
     * .orElseThrow(() -> new BadCredentialsException("Invalid token"));
     * var user = verificationToken.getUser();
     * user.setStatus(UserStatus.ACTIVE);
     * userRepository.save(user);
     * verificationRepository.delete(verificationToken);
     */
    return new VerifyResponse("success");
  }

  @Override
  public LoginRes login(UserLoginRequest request) {
    var user = userRepository.findByEmail(request.email()).orElseThrow(UserNotFoundException::new);
    if (user != null && !bCryptPasswordEncoder.matches(request.password(), user.getPassword())) {
      throw new BadCredentialsException("Bad credentials");
    }
    if (user.getStatus() == UserStatus.INACTIVE) {
      return new LoginRes("inactive", "", "", "", "", user.getId(), user.getEmail(), "");
    }
    if (user.getStatus() == UserStatus.BAN) {
      return new LoginRes("ban", "", "", "", "", user.getId(), user.getEmail(), "");
    }
    String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
    String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
    return new LoginRes("active", user.getFullName(), user.getBio(), token,
        refreshToken, user.getId(), user.getEmail(), user.getAvatar());
  }

  @Override
  public UserInfoRes updatePassword(Integer id, UserUpdatePasswordReq userUpdatePasswordReq) {
    var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    if (!bCryptPasswordEncoder.matches(userUpdatePasswordReq.password(), user.getPassword())) {
      throw new BadCredentialsException("Bad credentials");
    }
    user.setPassword(bCryptPasswordEncoder.encode(userUpdatePasswordReq.newPassword()));
    userRepository.save(user);
    return new UserInfoRes(user.getFullName(), user.getBio(), user.getEmail(),
        user.getAvatar(), user.getFriends().size(), user.getPosts().size());
  }

  @Override
  public LoginRes refreshToken(RequestTokenReq request) {
    // Validate the refresh token
    if (!jwtUtil.validateToken(request.refreshToken())) {
      throw new IllegalArgumentException("Invalid or expired refresh token");
    }
    // Extract the user's email from the refresh token
    String email = jwtUtil.getUsername(request.refreshToken());
    // Retrieve the user by email
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    // Generate new tokens
    String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
    String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());

    // Return the response
    LoginRes response = new LoginRes(
        "active",
        user.getFullName(),
        user.getBio(),
        newAccessToken,
        newRefreshToken,
        user.getId(),
        user.getEmail(),
        user.getAvatar());

    return response;
  }

  @Override
  public void forgotPassword(String email) {
    var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    String newPassword = generateRandomPassword(8);
    user.setPassword(newPassword);
    userRepository.save(user);
  }

}
