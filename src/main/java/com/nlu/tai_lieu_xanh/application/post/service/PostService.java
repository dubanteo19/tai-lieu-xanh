package com.nlu.tai_lieu_xanh.application.post.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;

public interface PostService {
  List<PostResponse> findAllPost(Pageable pageable);

}
