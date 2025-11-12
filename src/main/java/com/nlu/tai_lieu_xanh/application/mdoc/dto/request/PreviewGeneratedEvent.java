package com.nlu.tai_lieu_xanh.dto.request.m.doc;

public record PreviewGeneratedEvent(
    Integer mDocId,
    String prefix,
    int numPages) {

}
