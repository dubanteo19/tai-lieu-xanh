package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String content; // The content of the notification

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.UNREAD; // Default status is UNREAD

    private LocalDateTime createdDate = LocalDateTime.now(); // The timestamp when the notification is created

}
