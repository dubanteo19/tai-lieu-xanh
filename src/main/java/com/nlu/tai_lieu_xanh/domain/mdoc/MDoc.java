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
  @Enumerated(EnumType.STRING)
  private FileType fileType;
  @Column(name = "`pages`")
  private int pages;
  private Long fileSize;
  @ColumnDefault("0")
  @Column(nullable = false)
  private int downloads = 0;
  private int previewCount = 0;

  protected MDoc() {
  }

  private MDoc(String fileName, Long fileSize, int pages, FileType fileType) {
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.pages = pages;
    this.fileType = fileType;

  }

  public void increaseDownloadCount() {
    this.downloads++;
  }

  public void setPreviewCount(int previewCount) {
    this.previewCount = previewCount;

  }

  public static MDoc create(String fileName, Long fileSize, int pages, FileType fileType) {
    return new MDoc(fileName, fileSize, pages, fileType);
  }
}
