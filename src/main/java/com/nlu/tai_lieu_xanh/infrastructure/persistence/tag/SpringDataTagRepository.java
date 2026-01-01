package com.nlu.tai_lieu_xanh.infrastructure.persistence.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nlu.tai_lieu_xanh.domain.tag.Tag;

public interface SpringDataTagRepository extends JpaRepository<Tag, Integer> {
  Optional<Tag> findByName(String name);

  List<Tag> findByNameIn(List<String> names);
}
