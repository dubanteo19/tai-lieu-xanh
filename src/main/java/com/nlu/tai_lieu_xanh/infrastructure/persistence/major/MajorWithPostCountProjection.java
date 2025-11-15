package com.nlu.tai_lieu_xanh.infrastructure.persistence.major;

public interface MajorWithPostCountProjection {
  Integer getId();

  String getMajorName();

  Long getPostCount();
}
