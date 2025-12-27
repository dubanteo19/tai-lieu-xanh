package com.nlu.tai_lieu_xanh.interfaces.rest.notification;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.notification.dto.response.NotificationResponse;
import com.nlu.tai_lieu_xanh.application.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
  private final NotificationService notificationService;

  // Get unread notifications for the logged-in user
  @GetMapping("/unread")
  public ResponseEntity<List<NotificationResponse>> getUnreadNotifications() {
    List<NotificationResponse> notifications = notificationService.getUnreadNotifications();
    return ResponseEntity.ok(notifications);
  }

  @GetMapping("/user/{userId}/all")
  public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
    List<NotificationResponse> notifications = notificationService.getAllNotifications();
    return ResponseEntity.ok(notifications);
  }

  // Mark a notification as read
  @PostMapping("/markAsRead/{notificationId}")
  public ResponseEntity<String> markAsRead(@PathVariable Long notificationId) {
    notificationService.markAsRead(notificationId);
    return ResponseEntity.ok("Notification marked as read");
  }

}
