package com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc;

public record PreviewEvent(
    Integer MDocId,
    int numPages,
    String filePath) {
}
