package com.nlu.tai_lieu_xanh.application.user.service;

import com.nlu.tai_lieu_xanh.domain.user.User;

public interface UserService {
  User findById(Long userId);

  void activateUser(Long userId);
}
