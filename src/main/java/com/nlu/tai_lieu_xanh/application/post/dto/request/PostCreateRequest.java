package com.nlu.tai_lieu_xanh.application.post.dto.request;

import java.util.List;

public record PostCreateRequest(
    String title,
    String description,
    Long majorId,
    List<String> tags) {
}
