package com.nlu.tai_lieu_xanh.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(name = "`pages`")
    Integer pages;
    Long fileSize;
    @ColumnDefault("0")
    @Column(nullable = false)
    Integer downloads = 0;

}
