package com.nlu.tai_lieu_xanh.dto.response.tag;

public record TagWithPostsRes(
        Integer id,
        String tagName,
        Long posts
) {
}
