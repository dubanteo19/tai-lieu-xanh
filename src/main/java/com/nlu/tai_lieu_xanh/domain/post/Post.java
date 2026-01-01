package com.nlu.tai_lieu_xanh.domain.post;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nlu.tai_lieu_xanh.domain.comment.Comment;
import com.nlu.tai_lieu_xanh.domain.major.Major;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.tag.Tag;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "posts")
public class Post extends AbstractModel {
  @ManyToOne
  @JoinColumn(name = "author_id")
  User author;
  String title;
  @Lob
  @Column(columnDefinition = "text")
  String description;
  String thumb;
  @OneToOne
  @JoinColumn(name = "mdoc_id")
  MDoc mdoc;
  @ManyToOne
  @JoinColumn(name = "major_id")
  Major major;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
  List<Comment> comments = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  Set<Tag> tags = new HashSet<>();

  @Enumerated(EnumType.STRING)
  PostStatus postStatus = PostStatus.PUBLISHED;
  int viewCount = 0;
  int likeCount = 0;

  protected Post() {
  }

  public Post createPost() {
    return new Post();
  }

  public void view() {
    this.viewCount++;
  }

  public void delete() {
    this.postStatus = PostStatus.DELETED;
  }

  public void reject() {
    this.postStatus = PostStatus.REJECTED;
  }

  public void setMajor(Major major) {
    this.major = major;
  }

  public void setAuthor(User user) {
    this.author = user;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public void setMDoc(MDoc mdoc) {
    this.mdoc = mdoc;
  }

}
