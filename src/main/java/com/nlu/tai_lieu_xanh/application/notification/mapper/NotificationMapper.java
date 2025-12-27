package com.nlu.tai_lieu_xanh.application.notification.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.notification.dto.response.NotificationResponse;
import com.nlu.tai_lieu_xanh.application.shared.SharedMapper;
import com.nlu.tai_lieu_xanh.domain.notification.Notification;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationMapper {
  private final SharedMapper sharedMapper;

  public NotificationResponse toNotificationResponse(Notification notification) {
    String createdDate = sharedMapper.formatDate(notification.getCreatedDate());
    return new NotificationResponse(
        notification.getId(),
        notification.getContent(),
        notification.getStatus().toString(),
        createdDate);
  }

  public List<NotificationResponse> toNotificationResponseList(List<Notification> notificationList) {
    return notificationList
        .stream()
        .map(this::toNotificationResponse)
        .toList();
  }
}
