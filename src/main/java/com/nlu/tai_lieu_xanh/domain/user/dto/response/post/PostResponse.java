package com.nlu.tai_lieu_xanh.dto.response.post;

import java.util.List;

public record PostResponse(
        Integer id,
        String title,
        String thumb,
        MajorRes major,
        String status,
        List<String> tags,
        Author author,
        Integer views,
        Integer comments,
        Integer downloads,
        String createdDate
) {
}
