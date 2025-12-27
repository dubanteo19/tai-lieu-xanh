package com.nlu.tai_lieu_xanh.application.post.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;

public interface AdminPostService {
  List<PostResponse> getAllDeletedPost(Pageable pageable);

  List<PostResponse> getAllPost(Pageable pageable);

  void delete(Integer id);

  void rejectPost(Integer id, String reason);

  List<PostResponse> getAllReviewPost(Pageable pageable);
}
