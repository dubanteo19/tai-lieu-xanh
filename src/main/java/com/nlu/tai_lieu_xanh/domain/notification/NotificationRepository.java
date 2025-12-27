package com.nlu.tai_lieu_xanh.domain.notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
  Notification save(Notification notification);

  Optional<Notification> findById(Long id);

  List<Notification> findByUserId(Long userId);
}
