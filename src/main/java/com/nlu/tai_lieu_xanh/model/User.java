package com.nlu.tai_lieu_xanh.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String email;
    String password;
    String fullName;
    @Column(length = 300)
    String bio;
    String avatar;
    @Enumerated(EnumType.STRING)
    Role role = Role.USER;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserStatus status = UserStatus.INACTIVE;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    List<Post> posts = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    List<User> friends = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<Comment> comments = new ArrayList<>();
}
