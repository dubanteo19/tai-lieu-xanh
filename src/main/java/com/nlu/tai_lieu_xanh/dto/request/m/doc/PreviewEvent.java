package com.nlu.tai_lieu_xanh.dto.request.m.doc;

public record PreviewEvent(
    Integer MDocId,
    int numPages,
    String filePath) {
}
