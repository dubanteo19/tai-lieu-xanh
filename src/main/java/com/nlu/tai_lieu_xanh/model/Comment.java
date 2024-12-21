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
    @JoinColumn(name = "post_id")
    Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
