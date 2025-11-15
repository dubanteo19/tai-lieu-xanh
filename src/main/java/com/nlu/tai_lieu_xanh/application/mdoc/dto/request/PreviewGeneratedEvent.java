package com.nlu.tai_lieu_xanh.application.mdoc.dto.request;

public record PreviewGeneratedEvent(
    Integer mDocId,
    String prefix,
    int numPages) {

}
