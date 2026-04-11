package com.nlu.tai_lieu_xanh.application.post.service;

import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.shared.response.CursorResponse;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

  CursorResponse<PostResponse> findPublishedPosts(LocalDateTime cursor);

  PostResponse create(PostCreateRequest request, MultipartFile file);

  Post performSave(PostCreateRequest request, MDoc mdoc, Long userId);

  void viewPost(Long id);

  PostDetailResponse findPostDetail(Long postId);
}
