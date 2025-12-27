package com.nlu.tai_lieu_xanh.application.post.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.application.post.service.AdminPostService;
import com.nlu.tai_lieu_xanh.application.tag.service.TagService;
import com.nlu.tai_lieu_xanh.domain.post.PostRepository;
import com.nlu.tai_lieu_xanh.domain.post.PostSpecification;
import com.nlu.tai_lieu_xanh.domain.post.PostStatus;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPostServiceImpl implements AdminPostService {
  private final PostRepository postRepository;
  private final PostMapper postMapper;

  @Override
  public List<PostResponse> getAllDeletedPosts(Pageable pageable) {
    postRepository.getAllDeletedPosts(pageable);
  }

  @Override
  public List<PostResponse> getAll(Pageable pageable) {

  }

  @Override
  public void delete(Long id) {
    var post = findById(id);
    postRepository.delete(post);
  }

  @Override
  public void rejectPost(Long id, String reason) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'rejectPost'");
  }

  @Override
  public List<PostResponse> findAllReviewPost(Pageable pageable) {
    var spec = PostSpecification.isReview();
    return postRepository.findAll(spec, pageable)
        .stream()
        .map(postMapper::toPostResponse)
        .toList();
  }

  @Transactional
  public void setPostStatus(Long id, PostStatus postStatus) {
    var post = findById(id);
    post.setPostStatus(postStatus);
  }

  public List<PostResponse> findAllReviewPost(Pageable pageable) {
  }

  public void rejectPost(Long id, PostStatus postStatus, String reason) {
    var post = findById(id);
    var userId = post.getAuthor().getId();
    post.setPostStatus(postStatus);
    notificationService.createNotification(userId, "Tài liệu của bạn đã bị từ chối vì lý do %s".formatted(reason));
  }

  @Override
  public List<PostResponse> getAllPost(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllPost'");
  }

  @Override
  public List<PostResponse> getAllReviewPost(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllReviewPost'");
  }
}
