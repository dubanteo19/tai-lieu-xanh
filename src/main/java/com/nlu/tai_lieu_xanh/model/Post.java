package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "posts")
public class Post extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
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
    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> tags = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    PostStatus postStatus = PostStatus.REVIEWING;
    int views = 0;
    int likes = 0;

}
