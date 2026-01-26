package com.nlu.tai_lieu_xanh.domain.report;

import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "post_report")
public class PostReport extends AbstractModel {

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(EnumType.STRING)
  private ReportReason reason;

  @Enumerated(EnumType.STRING)
  private ReportStatus status = ReportStatus.PENDING;

  protected PostReport() {
  }
}
