package com.nlu.tai_lieu_xanh.application.comment.dto.request;

public record CommentCreateReq(
        Integer postId,
        Integer userId,
        String content
) {
}
