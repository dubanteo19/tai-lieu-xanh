package com.nlu.tai_lieu_xanh.application.post.dto.request;

import java.util.List;

public record PostCreateRequest(
    String title,
    String description,
    int authorId,
    int majorId,
    List<String> tags) {
}
