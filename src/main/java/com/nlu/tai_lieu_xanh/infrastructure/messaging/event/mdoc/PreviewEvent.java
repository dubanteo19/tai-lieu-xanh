package com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc;

public record PreviewEvent(
    Long MDocId,
    int numPages,
    String filePath) {
}
