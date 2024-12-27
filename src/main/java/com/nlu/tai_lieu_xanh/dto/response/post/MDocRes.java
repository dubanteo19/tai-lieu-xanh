package com.nlu.tai_lieu_xanh.dto.response.post;

public record MDocRes(
        Integer id,
        String fileName,
        String fileType,
        Integer pages,
        Integer downloads,
        long fileSize,
        String url
) {
}
