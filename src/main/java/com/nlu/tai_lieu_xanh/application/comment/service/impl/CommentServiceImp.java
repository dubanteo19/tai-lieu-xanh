package com.nlu.tai_lieu_xanh.application.comment.service.impl;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentCreateRequest;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentUpdateRequest;
import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentResponse;
import com.nlu.tai_lieu_xanh.application.comment.mapper.CommentMapper;
import com.nlu.tai_lieu_xanh.application.comment.service.CommentService;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import com.nlu.tai_lieu_xanh.domain.comment.Comment;
import com.nlu.tai_lieu_xanh.domain.comment.CommentRepository;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.exception.CommentNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {
  private final CommentRepository commentRepository;
  private final EntityManager entityManager;
  private final CommentMapper commentMapper;
  private final AuthService authService;

  @Override
  public CommentResponse save(CommentCreateRequest request) {
    var userRef = entityManager.getReference(User.class, request.userId());
    var postRef = entityManager.getReference(Post.class, request.postId());
    var comment = Comment.create(postRef, userRef, request.content());
    return commentMapper.toCommentRes(commentRepository.save(comment));
  }

  @Override
  public List<CommentResponse> getAllByPostId(Integer postId) {
    var comments = commentRepository.findAllByPostId(postId);
    return commentMapper.tocommentResponseList(comments);
  }

  @Override
  public void delete(Integer commentId) {
    var comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("comment not found"));
    comment.delete();
    commentRepository.save(comment);
  }

  @Override
  public List<CommentResponse> getAll() {
    throw new UnsupportedOperationException("Unimplemented method 'getAll'");
  }

  @Override
  @Transactional
  public CommentResponse update(CommentUpdateRequest request) {
    var currentUserId = authService.getCurrentUserId();
    var comment = commentRepository
        .findById(request.commentId())
        .orElseThrow(CommentNotFoundException::new);
    if (!comment.getUser().getId().equals(currentUserId))
      throw new AccessDeniedException("You have no permission to update this comment");
    comment.updateContent(request.content());
    return commentMapper.toCommentRes(comment);
  }
}
