package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String token;
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
    private LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(60);
}
