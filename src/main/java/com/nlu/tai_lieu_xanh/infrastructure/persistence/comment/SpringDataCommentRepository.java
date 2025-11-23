package com.nlu.tai_lieu_xanh.infrastructure.persistence.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.tai_lieu_xanh.domain.comment.Comment;

public interface SpringDataCommentRepository extends JpaRepository<Comment, Integer> {

  @Query("""
      SELECT c FROM Comment c
      WHERE c.post.id = :postId
      ORDER BY c.createdDate ASC
        """)
  List<Comment> findAllByPostId(@Param("postId") Integer postId);

}
