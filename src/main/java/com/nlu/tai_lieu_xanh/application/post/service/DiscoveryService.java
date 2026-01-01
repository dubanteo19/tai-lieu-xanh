package com.nlu.tai_lieu_xanh.application.post.service;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;

public interface DiscoveryService {

  List<PostResponse> getNewPosts();

  List<PostResponse> getHotPosts();

  List<PostResponse> getRelatedPosts(Long postId);

  List<PostResponse> searchPosts(String fileType, Long majorId, List<String> tags,
      String keyword, String sortBy, String direction, int page, int size);

  List<PostResponse> getPostsByIds(String Ids);

}
