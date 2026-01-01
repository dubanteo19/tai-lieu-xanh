package com.nlu.tai_lieu_xanh.infrastructure.persistence.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nlu.tai_lieu_xanh.domain.notification.Notification;

public interface SpringDataNotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findByUserId(Long userId);
}
