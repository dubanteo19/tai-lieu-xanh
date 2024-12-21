package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.dto.response.LoginRes;
import com.nlu.tai_lieu_xanh.exception.EmailExistException;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.mapper.UserMapper;
import com.nlu.tai_lieu_xanh.model.User;
import com.nlu.tai_lieu_xanh.model.UserStatus;
import com.nlu.tai_lieu_xanh.repository.UserRepository;
import com.nlu.tai_lieu_xanh.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
    UserRepository userRepository;
    JwtUtil jwtUtil;
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

    public User findById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void approveUser(Integer userId) {
        var user = findById(userId);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public LoginRes login(UserLoginRequest request) {
        var user = userRepository
                .findUserByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);
        if (user != null && bCryptPasswordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        return new LoginRes(token, refreshToken, user.getId(), user.getEmail());
    }
}