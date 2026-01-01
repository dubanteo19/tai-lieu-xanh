package com.nlu.tai_lieu_xanh.application.post.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.application.post.service.AdminPostService;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.post.PostRepository;
import com.nlu.tai_lieu_xanh.exception.PostNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPostServiceImpl implements AdminPostService {
  private final PostRepository postRepository;
  private final PostMapper postMapper;

  @Override
  @Transactional
  public void delete(Long id) {
    var post = findById(id);
    post.delete();
  }

  @Override
  @Transactional
  public void rejectPost(Long id, String reason) {
    var post = findById(id);
    post.reject();
  }

  @Override
  public List<PostResponse> getAllPost(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllPost'");
  }

  @Override
  public List<PostResponse> getAllReviewPost(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllDeletedPost'");
  }

  @Override
  public List<PostResponse> getAllDeletedPost(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllDeletedPost'");
  }

  @Override
  public Post findById(Long id) {
    return postRepository.findById(id)
        .orElseThrow(() -> new PostNotFoundException("post with id " + id + " not found"));
  }

  @Override
  public void approvePost(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'approvePost'");
  }
}
