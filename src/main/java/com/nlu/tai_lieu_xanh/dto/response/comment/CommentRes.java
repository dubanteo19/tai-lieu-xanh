package com.nlu.tai_lieu_xanh.dto.response.comment;

import com.nlu.tai_lieu_xanh.dto.response.post.Author;

public record CommentRes(
        Integer id,
        Author author,
        String content,
        String createdDate,
        String status
) {
}
