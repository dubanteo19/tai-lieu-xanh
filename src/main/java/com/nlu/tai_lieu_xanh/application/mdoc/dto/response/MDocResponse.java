package com.nlu.tai_lieu_xanh.application.mdoc.dto.response;

public record MDocResponse(
    Integer id,
    String fileName,
    String fileType,
    Integer pages,
    Integer downloads,
    long fileSize,
    String url) {
}
