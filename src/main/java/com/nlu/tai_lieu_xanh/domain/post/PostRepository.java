package com.nlu.tai_lieu_xanh.domain.post;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface PostRepository {
  List<Post> getAllReviewPosts(Pageable pageable);

  List<Post> getAllRejectedPosts(Pageable pageable);

  List<Post> getAllDeletedPosts(Pageable pageable);

  List<Post> getAll(Pageable pageable);

  void rejectPost(Long id);
}
