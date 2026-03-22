package com.nlu.tai_lieu_xanh.infrastructure.persistence.post;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.tai_lieu_xanh.domain.post.Post;

public interface SpringDataPostRepository extends JpaRepository<Post, Long> {

  @Query("""
        SELECT p from Post p
        WHERE (:cursor IS NULL OR p.modifiedDate < :cursor)
        ORDER BY p.modifiedDate DESC
      """)
  List<Post> findNextPosts(
      @Param("cursor") LocalDateTime cursor,
      Pageable pageable);
}
