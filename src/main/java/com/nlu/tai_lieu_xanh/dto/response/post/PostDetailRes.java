package com.nlu.tai_lieu_xanh.dto.response.post;

import java.util.List;

public record PostDetailRes(
    Integer id,
    String title,
    String description,
    MDocRes mdoc,
    Author author,
    String createdDate,
    MajorRes major,
    List<TagRes> tags) {
}
