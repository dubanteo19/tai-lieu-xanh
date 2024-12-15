package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comments")
public class Comment extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String content;
    @ManyToOne
    Comment parent;
    @ManyToOne
    @JoinColumn(name = "post_id",
            foreignKey = @ForeignKey(name = "FK_COMMENT_POST_ID"))
    Post post;
    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "FK_COMMENT_USER_ID")
    )
    User user;
}
