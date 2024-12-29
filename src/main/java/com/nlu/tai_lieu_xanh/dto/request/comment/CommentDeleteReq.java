package com.nlu.tai_lieu_xanh.dto.request.comment;

public record CommentDeleteReq(
        Integer commentId,
        Integer postId,
        Integer userId
) {
}
