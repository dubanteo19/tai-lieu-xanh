package com.nlu.tai_lieu_xanh.domain.major;

import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "major")
public class Major extends AbstractModel {
  @Column(nullable = false, unique = true)
  String name;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  MajorStatus majorStatus = MajorStatus.ACTIVE;

  protected Major() {
  }

  private Major(String name) {
    this.name = name;
  }

  public static Major create(String name) {
    return new Major(name);
  }

  public void updateName(String name) {
    this.name = name;
  }
}
