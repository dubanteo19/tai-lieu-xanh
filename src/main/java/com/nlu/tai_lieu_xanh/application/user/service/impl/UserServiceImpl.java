package com.nlu.tai_lieu_xanh.application.user.service.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.user.dto.response.UserProfileResponse;
import com.nlu.tai_lieu_xanh.application.user.service.UserService;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;

  public User findById(Integer userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }

  public UserProfileResponse findInfoById(Integer userId) {
    var user = findById(userId);
    return new UserProfileResponse(
        user.getFullName(),
        user.getBio(),
        user.getEmail(),
        user.getAvatar(),
        user.getFriends().size(),
        user.getPosts().size());
  }

  public UserProfileResponse updateInfo(Integer id, String fullName, String bio, MultipartFile avatar) {
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
  public void activateUser(Integer userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'activateUser'");
  }

}
