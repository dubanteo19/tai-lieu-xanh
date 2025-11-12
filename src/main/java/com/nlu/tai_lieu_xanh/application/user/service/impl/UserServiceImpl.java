package com.nlu.tai_lieu_xanh.application.user;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.post.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserInfoRes;
import com.nlu.tai_lieu_xanh.application.user.dto.response.UserRes;
import com.nlu.tai_lieu_xanh.application.user.mapper.UserMapper;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.post.PostStatus;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.domain.user.UserStatus;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.repository.PostRepository;
import com.nlu.tai_lieu_xanh.repository.VerificationRepository;
import com.nlu.tai_lieu_xanh.service.MailService;
import com.nlu.tai_lieu_xanh.service.PostService;
import com.nlu.tai_lieu_xanh.utils.JwtUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Service
public class UserServiceImpl {
  private UserRepository userRepository;
  private PostService postService;
  private VerificationRepository verificationRepository;
  private JwtUtil jwtUtil;
  private UserMapper userMapper;
  private PostMapper postMapper;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private MailService mailService;
  private private final PostRepository postRepository;

  public User findById(Integer userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
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

  public UserInfoRes updateInfo(Integer id, String fullName, String bio, MultipartFile avatar) {
    var user = findById(id);
    user.setFullName(fullName);
    user.setBio(bio);
    if (avatar != null && !avatar.isEmpty()) {
      String originalFilename = avatar.getOriginalFilename();
      String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
      String avatarPath = user.getId() + "-avatar" + extension;
      user.setAvatar("abc");
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

  public UserRes toUserRes(User user) {
    return new UserRes(user.getId(), user.getEmail(), user.getFullName(), user.getComments().size(),
        user.getPosts().size(), user.getStatus().toString());
  }

  public List<UserRes> findAll() {
    return userRepository.findAll().stream().map(this::toUserRes).collect(Collectors.toList());
  }

  public UserInfoRes updateStatus(Integer id, UserStatus status) {
    var user = findById(id);
    user.setStatus(status);
    return userMapper.toUserRes(userRepository.save(user));
  }

}
