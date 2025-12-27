package com.nlu.tai_lieu_xanh.application.notification.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.notification.dto.response.NotificationResponse;
import com.nlu.tai_lieu_xanh.application.notification.mapper.NotificationMapper;
import com.nlu.tai_lieu_xanh.application.notification.service.NotificationService;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import com.nlu.tai_lieu_xanh.domain.notification.Notification;
import com.nlu.tai_lieu_xanh.domain.notification.NotificationRepository;
import com.nlu.tai_lieu_xanh.domain.user.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationServerImpl implements NotificationService {
  private final EntityManager entityManager;
  private final NotificationRepository notificationRepository;
  private final AuthService authService;
  private final NotificationMapper notificationMapper;

  @Override
  public void createNotification(Long userId, String content) {
    var user = entityManager.getReference(User.class, userId);
    var notification = Notification.create(user, content);
    notificationRepository.save(notification);
  }

  @Override
  @Transactional
  public void markAsRead(Long notificationId) {
    var notification = notificationRepository
        .findById(notificationId)
        .orElseThrow();
    notification.markAsRead();
  }

  @Override
  public List<NotificationResponse> getAllNotifications() {
    Long currentUserId = authService.getCurrentUserId();
    var notificationList = notificationRepository.findByUserId(currentUserId);
    return notificationMapper.toNotificationResponseList(notificationList);
  }

  @Override
  public List<NotificationResponse> getUnreadNotifications() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUnreadNotifications'");
  }

}
