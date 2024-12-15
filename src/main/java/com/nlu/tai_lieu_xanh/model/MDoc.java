package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mdocs")
public class MDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String fileName;
    String url;
    @Enumerated(EnumType.STRING)
    FileType fileType;
    int page;
    long fileSize;
    int downloads;
}
