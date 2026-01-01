package com.nlu.tai_lieu_xanh.application.post.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.domain.post.Post;

public interface AdminPostService {
  List<PostResponse> getAllDeletedPost(Pageable pageable);

  List<PostResponse> getAllPost(Pageable pageable);

  List<PostResponse> getAllReviewPost(Pageable pageable);

  void delete(Long id);

  Post findById(Long id);

  void rejectPost(Long id, String reason);

  void approvePost(Long id);

}
