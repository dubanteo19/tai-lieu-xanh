package com.nlu.tai_lieu_xanh.application.post.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;

public interface PostService {

  List<PostResponse> findPublishedPosts(Pageable pageable);

  PostResponse create(PostCreateRequest request, MultipartFile file);

  void viewPost(Long id);

  PostDetailResponse findPostDetail(Long postId);

}
