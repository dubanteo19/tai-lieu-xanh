package com.nlu.tai_lieu_xanh.domain.user;

import java.util.ArrayList;
import java.util.List;

import com.nlu.tai_lieu_xanh.domain.comment.Comment;
import com.nlu.tai_lieu_xanh.domain.post.Post;

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
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private String fullName;
  private String title;
  private int level;
  @Column(length = 300)
  private String bio;
  private String avatar;
  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status = UserStatus.INACTIVE;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
  private List<Post> posts = new ArrayList<>();
  @ManyToMany
  @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
  private List<User> friends = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Comment> comments = new ArrayList<>();

  protected User() {

  }

  protected User(String email, String fullName, String password) {
    this.email = email;
    this.fullName = fullName;
    this.password = password;
  }

  public static User create(String email, String fullName, String password) {
    return new User(email, fullName, password);
  }

  public int countFriends() {
    return this.friends.size();
  }

  public int countPosts() {
    return this.posts.size();
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void activate() {
    this.status = UserStatus.ACTIVE;
  }

  public void updatePassword(String password) {
    this.password = password;
  }

}
