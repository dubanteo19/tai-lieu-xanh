package com.nlu.tai_lieu_xanh.application.comment.dto.response;

import com.nlu.tai_lieu_xanh.application.post.dto.response.AuthorResponse;

public record CommentResponse(
    Long id,
    AuthorResponse author,
    String content,
    String createdDate,
    String status) {
}
