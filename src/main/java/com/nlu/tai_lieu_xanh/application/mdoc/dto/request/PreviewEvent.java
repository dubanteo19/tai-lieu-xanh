package com.nlu.tai_lieu_xanh.application.mdoc.dto.response;

public record PreviewEvent(
    Integer MDocId,
    int numPages,
    String filePath) {
}
