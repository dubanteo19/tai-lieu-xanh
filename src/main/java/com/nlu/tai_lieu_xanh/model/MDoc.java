package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mdocs")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String fileName;
    String url;
    @Enumerated(EnumType.STRING)
    FileType fileType;
    int page = 0;
    long fileSize;
    int downloads = 0;
}
