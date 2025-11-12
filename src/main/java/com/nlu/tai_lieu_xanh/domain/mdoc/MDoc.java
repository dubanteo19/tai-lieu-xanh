package com.nlu.tai_lieu_xanh.domain.mdoc;

import org.hibernate.annotations.ColumnDefault;

import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "mdocs")
@Getter
public class MDoc extends AbstractModel {
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

  protected MDoc() {
  }
}
