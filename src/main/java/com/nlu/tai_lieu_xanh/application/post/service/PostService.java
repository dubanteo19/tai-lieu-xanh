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

  void viewPost(Integer id);

  PostDetailResponse getPostDetail(Integer postId);

  List<PostResponse> getNewPosts();

  List<PostResponse> getHotPosts();

  List<PostResponse> getRelatedPosts(Integer postId);

  List<PostResponse> searchPosts(String fileType, Integer majorId, List<String> tags,
      String keyword, String sortBy, String direction, int page, int size);

  List<PostResponse> getPostsByIds(String Ids);
}
