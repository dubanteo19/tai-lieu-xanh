package com.nlu.tai_lieu_xanh.domain.major;

import java.util.List;
import java.util.Optional;

import com.nlu.tai_lieu_xanh.infrastructure.persistence.major.MajorWithPostCountProjection;

public interface MajorRepository {
  List<Major> searchMajorByName(String name);

  List<Major> findAll();

  Optional<Major> findById(Integer id);

  Major save(Major major);

  void delete(Major major);

  List<MajorWithPostCountProjection> findAllMajorsWithPostCount();
}
