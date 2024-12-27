package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

import java.time.LocalDateTime;

@Entity
@Data
public class PostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ReportReason reason; // Changed to use ReportReason enum

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING; // PENDING, APPROVED, REJECTED

    private LocalDateTime createdDate = LocalDateTime.now();

    // Getters and Setters
}
