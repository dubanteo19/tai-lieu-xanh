package com.nlu.tai_lieu_xanh.application.notification.dto.response;

public record NotificationRes(
    Integer id,
    Integer userId,
    String content,
    String status,
    String createdDate) {
}
