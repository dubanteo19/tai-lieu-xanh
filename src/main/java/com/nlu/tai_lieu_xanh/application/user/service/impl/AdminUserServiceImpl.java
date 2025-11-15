package com.nlu.tai_lieu_xanh.application.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.user.service.UserService;

@Service
public class AdminUserServiceImpl {

  public List<UserService> findAll() {
    return userRepository.findAll().stream().map(this::toUserRes).collect(Collectors.toList());
  }

  public UserInfoRes updateStatus(Integer id, UserStatus status) {
    var user = findById(id);
    user.setStatus(status);
    return userMapper.toUserRes(userRepository.save(user));
  }

}
