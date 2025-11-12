package com.nlu.tai_lieu_xanh.domain.notification;

import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Notification extends AbstractModel {
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  private String content; // The content of the notification

  @Enumerated(EnumType.STRING)
  private NotificationStatus status = NotificationStatus.UNREAD; // Default status is UNREAD

  protected Notification() {
  }
}
