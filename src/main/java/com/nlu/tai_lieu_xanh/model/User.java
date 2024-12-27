package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.usertype.UserType;

import java.util.ArrayList;
import java.util.List;

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
