package com.nlu.tai_lieu_xanh.application.post.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;

/**
 * AdminPostService
 */
public interface AdminPostService {
  List<PostResponse> findAllDeletedPost(Pageable pageable);

  void delete(Integer id);
}
