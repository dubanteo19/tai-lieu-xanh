package com.nlu.tai_lieu_xanh.application.user.mapper;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserInfoRes;
import com.nlu.tai_lieu_xanh.domain.user.User;

@Component
public class UserMapper {

  public User toUser(UserCreateRequest request) {
    if (request == null)
      return null;
    return User.create(request.email(), request.fullName(), request.password());
  };

  public UserInfoRes toUserRes(User user) {
    return new UserInfoRes(
        user.getFullName(),
        user.getBio(),
        user.getEmail(),
        user.getAvatar(),
        user.countFriends(),
        user.countPosts());
  };

}
