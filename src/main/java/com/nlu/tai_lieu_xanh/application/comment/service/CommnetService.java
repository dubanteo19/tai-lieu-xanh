package com.nlu.tai_lieu_xanh.application.comment.service;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentCreateRequest;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentDeleteReq;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentUpdateRequest;
import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentResponse;

public interface CommnetService {
  List<CommentResponse> getAllCommentsByPostId(Integer postId);

  CommentResponse saveComment(Integer postId, CommentCreateRequest commentCreateRequest);

  CommentResponse updateComment(Integer postId, CommentUpdateRequest commentUpdateRequest);

  void deleteComment(Integer commentId);

  void deleteComment(Integer postId, CommentDeleteReq req);

  List<CommentResponse> getAllComments();
}
