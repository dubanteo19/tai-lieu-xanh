package com.nlu.tai_lieu_xanh.application.comment.dto.request;

public record CommentCreateRequest(
    Long postId,
    String content) {
}
