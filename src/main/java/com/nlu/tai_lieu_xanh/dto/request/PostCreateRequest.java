package com.nlu.tai_lieu_xanh.dto.request;

import com.nlu.tai_lieu_xanh.model.Post;

import java.util.List;

public record PostCreateRequest(
        String title,
        String description,
        int authorId,
        int majorId,
        List<TagRequest> tags
) {
}
