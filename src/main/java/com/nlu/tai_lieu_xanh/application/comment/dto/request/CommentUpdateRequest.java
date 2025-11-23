package com.nlu.tai_lieu_xanh.application.comment.dto.request;

public record CommentUpdateRequest(
    Integer commentId,
    String content) {
}
