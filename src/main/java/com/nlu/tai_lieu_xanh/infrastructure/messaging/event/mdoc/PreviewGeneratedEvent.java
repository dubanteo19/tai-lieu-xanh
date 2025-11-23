package com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc;

public record PreviewGeneratedEvent(
    Integer mDocId,
    String prefix,
    int numPages) {

}
