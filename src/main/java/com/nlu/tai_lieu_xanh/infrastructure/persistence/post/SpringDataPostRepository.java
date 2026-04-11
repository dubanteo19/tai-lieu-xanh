package com.nlu.tai_lieu_xanh.infrastructure.persistence.post;

import com.nlu.tai_lieu_xanh.domain.post.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataPostRepository extends JpaRepository<Post, Long> {

  @Query(
      """
        SELECT DISTINCT p from Post p
        LEFT JOIN FETCH p.tags
        WHERE (:cursor IS NULL OR p.createdDate < :cursor)
        ORDER BY p.createdDate DESC
      """)
  List<Post> findNextPosts(@Param("cursor") LocalDateTime cursor, Pageable pageable);
}
