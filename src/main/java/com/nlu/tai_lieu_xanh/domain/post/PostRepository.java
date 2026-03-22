package com.nlu.tai_lieu_xanh.domain.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

public interface PostRepository {
  List<Post> getAllReviewPosts(Pageable pageable);

  List<Post> getAllRejectedPosts(Pageable pageable);

  List<Post> getAllDeletedPosts(Pageable pageable);

  List<Post> findNextPosts(LocalDateTime cursor, Pageable pageable);

  void rejectPost(Long id);

  Optional<Post> findById(Long id);

  Post save(Post post);

  void viewPost(Long id);
}
