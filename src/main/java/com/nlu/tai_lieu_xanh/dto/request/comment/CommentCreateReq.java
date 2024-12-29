package com.nlu.tai_lieu_xanh.dto.request.comment;

public record CommentCreateReq(
        Integer postId,
        Integer userId,
        String content
) {
}
