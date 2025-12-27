package com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc;

public record PreviewGeneratedEvent(
    Long mDocId,
    String prefix,
    int numPages) {

}
