package com.nlu.tai_lieu_xanh.infrastructure.persistence.tag;

import java.util.List;
import java.util.Optional;

import com.nlu.tai_lieu_xanh.domain.tag.Tag;
import com.nlu.tai_lieu_xanh.domain.tag.TagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaTagRepository implements TagRepository {
  private final SpringDataTagRepository springDataTagRepository;

  @Override
  public Optional<Tag> findByName(String name) {
    return springDataTagRepository.findByName(name);
  }

  @Override
  public List<Tag> findAll() {
    return springDataTagRepository.findAll();
  }

  @Override
  public Tag save(Tag tag) {
    return springDataTagRepository.save(tag);
  }

  @Override
  public List<Tag> findByNameIn(List<String> names) {
    return springDataTagRepository.findbyNameIn(names);
  }

}
