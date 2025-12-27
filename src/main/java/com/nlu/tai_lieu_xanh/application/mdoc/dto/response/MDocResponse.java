package com.nlu.tai_lieu_xanh.application.mdoc.dto.response;

public record MDocResponse(
    Long id,
    String fileName,
    String fileType,
    int pages,
    int downloads,
    long fileSize) {
}
