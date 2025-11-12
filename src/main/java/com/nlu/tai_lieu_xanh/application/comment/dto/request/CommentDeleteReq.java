package com.nlu.tai_lieu_xanh.application.comment.dto.request;

public record CommentDeleteReq(
        Integer commentId,
        Integer postId,
        Integer userId
) {
}
