package com.nlu.tai_lieu_xanh.domain.tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
  Optional<Tag> findByName(String name);

  List<Tag> findAll();

  Tag save(Tag tag);

  List<Tag> findByNameIn(List<String> names);
}
