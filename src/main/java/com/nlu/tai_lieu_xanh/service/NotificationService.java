package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.response.notification.NotificationRes;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.mapper.SharedConfig;
import com.nlu.tai_lieu_xanh.model.Notification;
import com.nlu.tai_lieu_xanh.model.NotificationStatus;
import com.nlu.tai_lieu_xanh.model.User;
import com.nlu.tai_lieu_xanh.repository.NotificationRepository;
import com.nlu.tai_lieu_xanh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NotificationService {

    NotificationRepository notificationRepository;
    UserRepository userRepository;

    public NotificationRes createNotification(Integer userId, String content) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
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
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        var notificationList = notificationRepository.findByUserAndStatus(user, NotificationStatus.UNREAD, sort);
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
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        return notificationRepository.findByUser(user, sort).stream().map(this::toNotificationRes).toList();
    }
}
