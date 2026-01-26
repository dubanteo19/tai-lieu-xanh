package com.nlu.tai_lieu_xanh.domain.notification;

import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "notification")
public class Notification extends AbstractModel {
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  private String content;

  @Enumerated(EnumType.STRING)
  private NotificationStatus status = NotificationStatus.UNREAD;

  protected Notification() {
  }

  private Notification(User user, String content) {
    this.user = user;
    this.content = content;
  }

  public static Notification create(User user, String content) {
    return new Notification(user, content);
  }

  public void markAsRead() {
    this.status = NotificationStatus.READ;
  }
}
