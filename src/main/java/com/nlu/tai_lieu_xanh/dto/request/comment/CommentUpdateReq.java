package com.nlu.tai_lieu_xanh.dto.request.comment;

public record CommentUpdateReq(
        Integer commentId,
        Integer postId,
        Integer userId,
        String content
) {
}
