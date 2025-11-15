package com.nlu.tai_lieu_xanh.application.post.dto.response;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;

public record PostResponse(
    Integer id,
    String title,
    String thumb,
    MajorResponse major,
    String status,
    List<String> tags,
    AuthorResponse author,
    Integer views,
    Integer comments,
    Integer downloads,
    String createdDate) {
}
