package com.nlu.tai_lieu_xanh.dto.request.m.doc;

public record MDocReq(
        String fileName,
        String fileType,
        Integer pages,
        long fileSize,
        String url
) {
}
