package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;
    String title;
    String thumb;
    @OneToOne
    @JoinColumn(name = "doc_id")
    MDoc doc;
    int download;
    int views;


}
