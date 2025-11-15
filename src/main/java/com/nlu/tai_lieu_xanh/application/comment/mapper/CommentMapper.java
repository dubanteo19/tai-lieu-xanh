package com.nlu.tai_lieu_xanh.application.comment.mapper;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentResponse;
import com.nlu.tai_lieu_xanh.application.shared.SharedMapper;
import com.nlu.tai_lieu_xanh.domain.comment.Comment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentMapper {
  private final SharedMapper sharedMapper;

  public CommentResponse toCommentRes(Comment comment) {
    if (comment == null)
      return null;
    var author = sharedMapper.toAuthor(comment.getUser());
    var createdDate = sharedMapper.formatDate(comment.getCreatedDate());
    var status = sharedMapper.toStatus(comment.getStatus());
    return new CommentResponse(
        comment.getId(),
        author,
        comment.getContent(),
        createdDate,
        status);

  }
}
