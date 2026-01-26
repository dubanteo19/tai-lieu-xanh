package com.nlu.tai_lieu_xanh.domain.tag;

import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tag")
public class Tag extends AbstractModel {
  @Column(nullable = false, unique = true)
  private String name;

  protected Tag() {
  }

  protected Tag(String name) {
    this.name = name;
  }

  public static Tag create(String name) {
    return new Tag(name);
  }

}
