package com.nlu.tai_lieu_xanh.application.tag.dto.response;

public record TagWithPostCountsResponse(
    Long id,
    String tagName,
    Long posts) {
}
