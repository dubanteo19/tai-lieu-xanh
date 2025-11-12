package com.nlu.tai_lieu_xanh.dto.response.notification;

public record NotificationRes(
        Integer id,
        Integer userId,
        String content,
        String status,
        String createdDate
) {
}
