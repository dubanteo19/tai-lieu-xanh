package com.nlu.tai_lieu_xanh.application.mdoc.dto.request;

public record PreviewEvent(
    Integer MDocId,
    int numPages,
    String filePath) {
}
