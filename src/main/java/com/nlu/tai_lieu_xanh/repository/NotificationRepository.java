package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.model.Notification;
import com.nlu.tai_lieu_xanh.model.NotificationStatus;
import com.nlu.tai_lieu_xanh.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserAndStatus(User user, NotificationStatus status, Sort sort);

    List<Notification> findByUser(User user, Sort sort);
}
