package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.exception.EmailExistException;
import com.nlu.tai_lieu_xanh.mapper.UserMapper;
import com.nlu.tai_lieu_xanh.model.User;
import com.nlu.tai_lieu_xanh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper = UserMapper.INSTANCE;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User register(UserCreateRequest request) {
        var user = userMapper.toUser(request);
        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new EmailExistException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public Boolean login(UserLoginRequest request) {
        var user = userRepository.findUserByEmail(request.email());
        return (user != null && bCryptPasswordEncoder.matches(request.password(), user.getPassword()));

    }
}