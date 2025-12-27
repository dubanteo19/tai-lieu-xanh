package com.nlu.tai_lieu_xanh.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @CreatedDate
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime modifiedDate;

  public AbstractModel() {
  }
}
