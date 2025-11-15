package com.nlu.tai_lieu_xanh.application.mdoc.dto.request;

public record MDocRequest(
    String fileName,
    String fileType,
    Integer pages,
    long fileSize,
    String url) {
}
