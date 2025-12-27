package com.nlu.tai_lieu_xanh.application.post.dto.response;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.MDocResponse;
import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;

public record PostDetailResponse(
    Long id,
    String title,
    String description,
    MDocResponse mdoc,
    AuthorResponse author,
    MajorResponse major,
    List<TagResponse> tags,
    String createdDate) {
}
