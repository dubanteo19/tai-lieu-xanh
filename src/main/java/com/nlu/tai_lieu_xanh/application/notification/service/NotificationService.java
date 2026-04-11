package com.nlu.tai_lieu_xanh.application.notification.service;

import com.nlu.tai_lieu_xanh.application.notification.dto.response.NotificationResponse;
import java.util.List;

public interface NotificationService {
  void sendNotification(Long userId, String content);

  void markAsRead(Long notificationId);

  List<NotificationResponse> getAllNotifications();

  List<NotificationResponse> getUnreadNotifications();
}
