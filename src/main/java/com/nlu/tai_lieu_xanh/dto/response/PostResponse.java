package com.nlu.tai_lieu_xanh.dto.response;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public record PostResponse(
        Integer id,
        String title,
        String thumb,
        String major,
        List<String> tags,
        Author author,
        Integer views,
        Integer comments,
        Integer downloads
) {
}
