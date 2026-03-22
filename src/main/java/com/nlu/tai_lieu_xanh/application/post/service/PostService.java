package com.nlu.tai_lieu_xanh.application.post.service;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.shared.response.CursorResponse;

public interface PostService {

  CursorResponse<PostResponse> findPublishedPosts(LocalDateTime cursor);

  PostResponse create(PostCreateRequest request, MultipartFile file);

  void viewPost(Long id);

  PostDetailResponse findPostDetail(Long postId);

}
