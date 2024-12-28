package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.response.notification.NotificationRes;
import com.nlu.tai_lieu_xanh.service.NotificationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NotificationController {
    NotificationService notificationService;

    // Get unread notifications for the logged-in user
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationRes>> getUnreadNotifications(@PathVariable Integer userId) {
        List<NotificationRes> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<List<NotificationRes>> getUnreadNotificationsCount(@PathVariable Integer userId) {
        List<NotificationRes> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<NotificationRes>> getAllNotifications(@PathVariable Integer userId) {
        List<NotificationRes> notifications = notificationService.getAllNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // Mark a notification as read
    @PostMapping("/markAsRead/{notificationId}")
    public ResponseEntity<String> markAsRead(@PathVariable Integer notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read");
    }

    @PostMapping("/create")
    public ResponseEntity<NotificationRes> createNotification(@RequestParam Integer userId,
                                                              @RequestParam String content) {
        NotificationRes notification = notificationService.createNotification(userId, content);
        return ResponseEntity.ok(notification);
    }
}
