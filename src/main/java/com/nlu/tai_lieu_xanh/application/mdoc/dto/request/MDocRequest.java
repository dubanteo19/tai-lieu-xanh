package com.nlu.tai_lieu_xanh.application.mdoc.dto.request;

public record MDocRequest(
    String fileName,
    String fileType,
    int pages,
    long fileSize,
    String url) {
}
