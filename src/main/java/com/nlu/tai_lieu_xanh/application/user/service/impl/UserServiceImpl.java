package com.nlu.tai_lieu_xanh.application.user.service.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.post.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.application.post.service.PostService;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserInfoResponse;
import com.nlu.tai_lieu_xanh.application.user.mapper.UserMapper;
import com.nlu.tai_lieu_xanh.application.user.service.UserService;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.infrastructure.mail.MailService;
import com.nlu.tai_lieu_xanh.repository.PostRepository;
import com.nlu.tai_lieu_xanh.repository.VerificationRepository;
import com.nlu.tai_lieu_xanh.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  private PostService postService;
  private VerificationRepository verificationRepository;
  private JwtUtil jwtUtil;
  private UserMapper userMapper;
  private PostMapper postMapper;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private MailService mailService;

  public User findById(Integer userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public void activateUser(Integer userId) {
    var user = userRepository.findById(userId)
        .orElseThrow(UserNotFoundException::new);
    user.activate();
    userRepository.save(user);
  }

  public UserInfoResponse findInfoById(Integer userId) {
    var user = findById(userId);
    return new UserInfoResponse(
        user.getFullName(),
        user.getBio(),
        user.getEmail(),
        user.getAvatar(),
        user.getFriends().size(),
        user.getPosts().size());
  }

  public UserInfoResponse updateInfo(Integer id, String fullName, String bio, MultipartFile avatar) {
    /*
     * var user = findById(id);
     * user.setFullName(fullName);
     * user.setBio(bio);
     * if (avatar != null && !avatar.isEmpty()) {
     * String originalFilename = avatar.getOriginalFilename();
     * String extension =
     * originalFilename.substring(originalFilename.lastIndexOf("."));
     * String avatarPath = user.getId() + "-avatar" + extension;
     * user.setAvatar("abc");
     * }
     * userRepository.save(user);
     * return new UserInfoRes(user.getFullName(), user.getBio(), user.getEmail(),
     * user.getAvatar(), user.getFriends().size(), user.getPosts().size());
     */
    throw new NotImplementedException("not yet implement");
  }

}
