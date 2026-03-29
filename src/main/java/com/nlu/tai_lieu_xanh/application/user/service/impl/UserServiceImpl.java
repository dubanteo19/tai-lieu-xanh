package com.nlu.tai_lieu_xanh.application.user.service.impl;

import com.nlu.tai_lieu_xanh.application.user.dto.response.UserProfileResponse;
import com.nlu.tai_lieu_xanh.application.user.mapper.UserMapper;
import com.nlu.tai_lieu_xanh.application.user.service.UserService;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public User findById(Long userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public UserProfileResponse findInfoById(Long userId) {
    var user = findById(userId);
    return userMapper.toUserProfileResponse(user);
  }

  public UserProfileResponse updateInfo(
      Long id, String fullName, String bio, MultipartFile avatar) {
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

  @Override
  public void activateUser(Long userId) {
    throw new UnsupportedOperationException("Unimplemented method 'activateUser'");
  }
}
