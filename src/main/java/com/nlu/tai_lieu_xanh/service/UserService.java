package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.dto.request.UserLoginRequest;
import com.nlu.tai_lieu_xanh.dto.response.auth.LoginRes;
import com.nlu.tai_lieu_xanh.dto.response.auth.RegisterRes;
import com.nlu.tai_lieu_xanh.dto.response.auth.VerifyRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.dto.response.user.UserInfoRes;
import com.nlu.tai_lieu_xanh.dto.response.user.UserPostsRes;
import com.nlu.tai_lieu_xanh.dto.response.user.UserUpdateInfoReq;
import com.nlu.tai_lieu_xanh.dto.response.user.UserUpdatePasswordReq;
import com.nlu.tai_lieu_xanh.exception.EmailExistException;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.mapper.UserMapper;
import com.nlu.tai_lieu_xanh.model.*;
import com.nlu.tai_lieu_xanh.repository.PostRepository;
import com.nlu.tai_lieu_xanh.repository.UserRepository;
import com.nlu.tai_lieu_xanh.repository.VerificationRepository;
import com.nlu.tai_lieu_xanh.utils.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
    UserRepository userRepository;
    PostService postService;
    VerificationRepository verificationRepository;
    JwtUtil jwtUtil;
    UserMapper userMapper = UserMapper.INSTANCE;
    PostMapper postMapper = PostMapper.INSTANCE;
    FtpService ftpService;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    MailService mailService;
    private final PostRepository postRepository;

    public RegisterRes register(UserCreateRequest request) {
        var user = userMapper.toUser(request);
        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new EmailExistException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        var savedUser = userRepository.save(user);
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationRepository.save(verificationToken);
        try {
            mailService.sendVerificationEmail(user.getEmail(), token);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return new RegisterRes("Registered successfully", savedUser.getEmail(), savedUser.getId());
    }

    public User findById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public VerifyRes verifyAccount(String token) {
        System.out.println(token);
        var verificationToken = verificationRepository.findByToken((token)).orElseThrow(() -> new BadCredentialsException("Invalid token"));
        var user = verificationToken.getUser();
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        verificationRepository.delete(verificationToken);
        return new VerifyRes("success");
    }

    public LoginRes login(UserLoginRequest request) {
        var user = userRepository.findUserByEmail(request.email()).orElseThrow(UserNotFoundException::new);
        if (user != null && !bCryptPasswordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        if (user.getStatus() == UserStatus.INACTIVE) {
            return new LoginRes("inactive", "", "", "", "", user.getId(), user.getEmail(), "");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        return new LoginRes("active", user.getFullName(), user.getBio(), token,
                refreshToken, user.getId(), user.getEmail(), user.getAvatar());
    }

    public void approveUser(Integer id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public UserInfoRes findInfoById(Integer id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (!id.equals(user.getId())) {
            throw new IllegalArgumentException("id is not equal to user id");
        }
        return new UserInfoRes(user.getFullName(), user.getBio(), user.getEmail(),
                user.getAvatar(), user.getFriends().size(), user.getPosts().size());
    }

    public List<PostResponse> getPostsById(Integer id) {
        var user = findById(id);
        return user.getPosts()
                .stream()
                .filter(post -> post.getPostStatus() != PostStatus.DELETED)
                .sorted(Comparator.comparing(Post::getCreatedDate).reversed())
                .map(postMapper::toPostResponse).toList();
    }

    public UserInfoRes updatePassword(Integer id, UserUpdatePasswordReq userUpdatePasswordReq) {
        var user = findById(id);
        if (!bCryptPasswordEncoder.matches(userUpdatePasswordReq.password(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        user.setPassword(bCryptPasswordEncoder.encode(userUpdatePasswordReq.newPassword()));
        userRepository.save(user);
        return new UserInfoRes(user.getFullName(), user.getBio(), user.getEmail(),
                user.getAvatar(), user.getFriends().size(), user.getPosts().size());
    }

    public UserInfoRes updateInfo(Integer id, String fullName, String bio, MultipartFile avatar) {
        var user = findById(id);
        user.setFullName(fullName);
        user.setBio(bio);
        if (avatar != null && !avatar.isEmpty()) {
            String originalFilename = avatar.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String avatarPath = user.getId() + "-avatar" + extension;
            try {
                String path = ftpService.uploadTempFile(avatar.getInputStream(), avatarPath, user.getId());
                user.setAvatar(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        userRepository.save(user);
        return new UserInfoRes(user.getFullName(), user.getBio(), user.getEmail(),
                user.getAvatar(), user.getFriends().size(), user.getPosts().size());
    }

    public void deletePost(Integer id, Integer postId) {
        var post = postService.findById(postId);
        var user = findById(id);
        if (!user.getPosts().contains(post)) {
            throw new IllegalArgumentException("User does not have post " + postId);
        }
        post.setPostStatus(PostStatus.DELETED);
        postRepository.save(post);
    }
}