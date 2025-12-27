package com.nlu.tai_lieu_xanh.application.notification.dto.response;

public record NotificationResponse(
    Long id,
    String content,
    String status,
    String createdDate) {
}
