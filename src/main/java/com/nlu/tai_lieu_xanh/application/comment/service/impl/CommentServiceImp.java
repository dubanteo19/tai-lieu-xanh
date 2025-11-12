package com.nlu.tai_lieu_xanh.application.comment.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentCreateReq;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentDeleteReq;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentUpdateReq;
import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentRes;
import com.nlu.tai_lieu_xanh.application.comment.mapper.CommentMapper;
import com.nlu.tai_lieu_xanh.application.comment.service.CommnetService;
import com.nlu.tai_lieu_xanh.domain.comment.CommentStatus;
import com.nlu.tai_lieu_xanh.repository.CommentRepository;
import com.nlu.tai_lieu_xanh.service.PostService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommnetService {
  private final CommentRepository commentRepository;
  private final PostService postService;
  private final UserService userService;
  private final CommentMapper commentMapper;

  public List<CommentRes> getAllCommentsByPostId(Integer postId) {
    var post = postService.findById(postId);
    var comments = post.getComments();
    return comments.stream().map(commentMapper::toCommentRes).collect(Collectors.toList());
  }

  public CommentRes saveComment(Integer postId, CommentCreateReq commentCreateReq) {
    if (!postId.equals(commentCreateReq.postId())) {
      throw new IllegalArgumentException("postId is not match");
    }
    var user = userService.findById(commentCreateReq.userId());
    var post = postService.findById(postId);
    var comment = new Comment();
    comment.setContent(commentCreateReq.content());
    comment.setPost(post);
    comment.setUser(user);
    return commentMapper.toCommentRes(commentRepository.save(comment));
  }

  public CommentRes updateComment(Integer postId, CommentUpdateReq req) {
    if (!postId.equals(req.postId())) {
      throw new IllegalArgumentException("postId is not match");
    }
    var user = userService.findById(req.userId());
    if (!user.getId().equals(req.userId())) {
      throw new IllegalArgumentException("userId is not match");
    }
    var currentComment = commentRepository
        .findById(req.commentId()).orElseThrow(() -> new IllegalArgumentException("comment not found"));
    currentComment.setContent(req.content());
    return commentMapper.toCommentRes(commentRepository.save(currentComment));
  }

  public void deleteComment(Integer commentId) {
    var comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("comment not found"));
    comment.setStatus(CommentStatus.DELETED);
    commentRepository.save(comment);
  }

  public void deleteComment(Integer postId, CommentDeleteReq req) {
    if (!postId.equals(req.postId())) {
      throw new IllegalArgumentException("postId is not match");
    }
    var user = userService.findById(req.userId());
    if (!user.getId().equals(req.userId())) {
      throw new IllegalArgumentException("userId is not match");
    }
    var comment = commentRepository.findById(req.commentId())
        .orElseThrow(() -> new IllegalArgumentException("comment not found"));
    commentRepository.deleteById(comment.getId());
  }

  public List<CommentRes> getAllComments() {
    return commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"))
        .stream().map(commentMapper::toCommentRes).collect(Collectors.toList());
  }
}
