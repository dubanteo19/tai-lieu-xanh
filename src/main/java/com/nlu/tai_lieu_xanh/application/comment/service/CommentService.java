package com.nlu.tai_lieu_xanh.application.comment.service;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentCreateRequest;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentUpdateRequest;
import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentResponse;

public interface CommentService {
  List<CommentResponse> getAllByPostId(Long postId);

  CommentResponse save(CommentCreateRequest commentCreateRequest);

  CommentResponse update(CommentUpdateRequest commentUpdateRequest);

  void delete(Long commentId);

  List<CommentResponse> getAll();
}
