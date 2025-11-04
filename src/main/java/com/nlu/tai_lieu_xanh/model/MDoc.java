package com.nlu.tai_lieu_xanh.model;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mdocs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MDoc {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String fileName;
  private String url;
  @Enumerated(EnumType.STRING)
  private FileType fileType;
  @Column(name = "`pages`")
  private Integer pages;
  private Long fileSize;
  @ColumnDefault("0")
  @Column(nullable = false)
  private Integer downloads = 0;
  private int previewCount = 0;

  public void increaseDownloadCount() {
    this.downloads++;
  }
}
