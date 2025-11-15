package com.nlu.tai_lieu_xanh.application.tag.dto.response;

public record TagWithPostCountsResponse(
    Integer id,
    String tagName,
    Long posts) {
}
