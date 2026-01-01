package com.nlu.tai_lieu_xanh.infrastructure.persistence.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nlu.tai_lieu_xanh.domain.notification.Notification;
import com.nlu.tai_lieu_xanh.domain.notification.NotificationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JpaNotificationRepository implements NotificationRepository {
  private final SpringDataNotificationRepository springDataNotificationRepository;

  @Override
  public Notification save(Notification notification) {
    return springDataNotificationRepository.save(notification);
  }

  @Override
  public Optional<Notification> findById(Long id) {
    return springDataNotificationRepository.findById(id);
  }

  @Override
  public List<Notification> findByUserId(Long userId) {
    return springDataNotificationRepository.findByUserId(userId);
  }

}
