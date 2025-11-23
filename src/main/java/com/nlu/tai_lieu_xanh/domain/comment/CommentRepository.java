package com.nlu.tai_lieu_xanh.domain.comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
  Comment save(Comment comment);

  void delete(Comment comment);

  Optional<Comment> findById(Integer id);

  List<Comment> findAllByPostId(Integer postId);
}
