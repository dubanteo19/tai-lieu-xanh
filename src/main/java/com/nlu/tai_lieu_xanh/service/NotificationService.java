package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.response.notification.NotificationRes;
import com.nlu.tai_lieu_xanh.mapper.SharedConfig;
import com.nlu.tai_lieu_xanh.model.Notification;
import com.nlu.tai_lieu_xanh.model.NotificationStatus;
import com.nlu.tai_lieu_xanh.model.User;
import com.nlu.tai_lieu_xanh.repository.NotificationRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NotificationService {

    NotificationRepository notificationRepository;
    UserService userService;

    public NotificationRes createNotification(Integer userId, String content) {
        var user = userService.findById(userId);
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(content);
        return toNotificationRes(notificationRepository.save(notification));
    }

    public NotificationRes toNotificationRes(Notification n) {
        return new NotificationRes(n.getId(), n.getUser().getId(),
                n.getContent(), n.getStatus().toString(),
                SharedConfig.formatDate(n.getCreatedDate()));
    }

    // Get unread notifications for a user
    public List<NotificationRes> getUnreadNotifications(Integer userId) {
        var user = userService.findById(userId);
        var notificationList = notificationRepository.findByUserAndStatus(user, NotificationStatus.UNREAD);
        return notificationList.stream().map(this::toNotificationRes).toList();
    }

    // Mark a notification as read
    public NotificationRes markAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(NotificationStatus.READ);
        return toNotificationRes(notificationRepository.save(notification));
    }

    // Get all notifications for a user
    public List<NotificationRes> getAllNotifications(Integer userId) {
        var user = userService.findById(userId);
        return notificationRepository.findByUser(user).stream().map(this::toNotificationRes).toList();
    }
}
