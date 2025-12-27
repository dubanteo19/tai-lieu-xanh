package com.nlu.tai_lieu_xanh.application.notification.service;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.notification.dto.response.NotificationResponse;

public interface NotificationService {
  void createNotification(Long userId, String content);

  void markAsRead(Long notificationId);

  List<NotificationResponse> getAllNotifications();

  List<NotificationResponse> getUnreadNotifications();
}
