package com.nlu.tai_lieu_xanh.application.comment.service;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentCreateReq;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentDeleteReq;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentUpdateReq;
import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentRes;

public interface CommnetService {
  List<CommentRes> getAllCommentsByPostId(Integer postId);

  CommentRes saveComment(Integer postId, CommentCreateReq commentCreateReq);

  CommentRes updateComment(Integer postId, CommentUpdateReq req);

  void deleteComment(Integer commentId);

  void deleteComment(Integer postId, CommentDeleteReq req);

  List<CommentRes> getAllComments();
}
