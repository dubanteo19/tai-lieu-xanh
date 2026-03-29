package com.nlu.tai_lieu_xanh.application.user.mapper;

import com.nlu.tai_lieu_xanh.application.user.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserProfileResponse;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserSummary;
import com.nlu.tai_lieu_xanh.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toUser(UserCreateRequest request) {
    if (request == null) return null;
    return User.create(request.email(), request.fullName(), request.password());
  }

  public UserSummary toUserSummary(User user) {
    return new UserSummary(user.getId(), user.getEmail(), user.getFullName(), user.getAvatar());
  }

  public UserProfileResponse toUserProfileResponse(User user) {
    return new UserProfileResponse(
        user.getFullName(),
        user.getBio(),
        user.getEmail(),
        user.getAvatar(),
        user.getFriends().size(),
        user.getPosts().size());
  }
}
