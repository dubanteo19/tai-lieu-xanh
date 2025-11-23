package com.nlu.tai_lieu_xanh.infrastructure.persistence.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nlu.tai_lieu_xanh.domain.comment.Comment;
import com.nlu.tai_lieu_xanh.domain.comment.CommentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {
  private final SpringDataCommentRepository springDataCommentRepository;

  @Override
  public Comment save(Comment comment) {
    return springDataCommentRepository.save(comment);
  }

  @Override
  public void delete(Comment comment) {
    springDataCommentRepository.delete(comment);
  }

  @Override
  public Optional<Comment> findById(Integer id) {
    return springDataCommentRepository.findById(id);
  }

  @Override
  public List<Comment> findAllByPostId(Integer postId) {
    return springDataCommentRepository.findAllByPostId(postId);
  }

}
