package com.nlu.tai_lieu_xanh.dto.request.post;

import com.nlu.tai_lieu_xanh.dto.request.m.doc.MDocReq;

import java.util.List;

public record PostCreateRequest(
        String title,
        String description,
        int authorId,
        int majorId,
        List<String> tags
) {
}
