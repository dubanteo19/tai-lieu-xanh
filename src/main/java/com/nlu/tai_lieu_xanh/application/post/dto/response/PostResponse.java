package com.nlu.tai_lieu_xanh.application.post.dto.response;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;

public record PostResponse(
    Long id,
    String title,
    String thumb,
    String status,
    MajorResponse major,
    AuthorResponse author,
    List<TagResponse> tags,
    MetaData meta) {
}
