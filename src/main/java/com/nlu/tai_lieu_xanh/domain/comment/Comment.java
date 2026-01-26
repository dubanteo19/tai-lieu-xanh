package com.nlu.tai_lieu_xanh.domain.comment;

import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends AbstractModel {
  private String content;
  @Enumerated(EnumType.STRING)
  private CommentStatus status = CommentStatus.ACTIVE;
  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  protected Comment() {

  }

  private Comment(Post post, User user, String content) {
    this.post = post;
    this.user = user;
    this.content = content;
  }

  public void delete() {
    this.status = CommentStatus.DELETED;
  }

  public static Comment create(Post post, User user, String content) {
    return new Comment(post, user, content);
  }

  public void updateContent(String newContent) {
    this.content = newContent;
  }
}
