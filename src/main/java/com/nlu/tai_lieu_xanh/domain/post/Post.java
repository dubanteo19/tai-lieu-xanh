package com.nlu.tai_lieu_xanh.domain.post;

import java.util.ArrayList;
import java.util.List;

import com.nlu.tai_lieu_xanh.domain.comment.Comment;
import com.nlu.tai_lieu_xanh.domain.major.Major;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
  @JoinColumn(name = "doc_id")
  MDoc doc;
  @ManyToOne
  @JoinColumn(name = "major_id")
  Major major;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
  List<Comment> comments = new ArrayList<>();

  @ElementCollection
  @Column(name = "tag_id")
  List<Integer> tagIds = new ArrayList<>();
  @Enumerated(EnumType.STRING)
  PostStatus postStatus = PostStatus.REVIEWING;
  int views = 0;
  int likes = 0;

  protected Post() {
  }

  public createPost(){
    return new Post(); 
  }

  public void addTag(Integer tagId) {
    this.tagIds.add(tagId);
  }

  public void removeTag(Integer tagId) {
    this.tagIds.remove(tagId);
  }
}
