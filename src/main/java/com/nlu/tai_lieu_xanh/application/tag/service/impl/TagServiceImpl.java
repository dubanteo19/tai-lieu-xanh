package com.nlu.tai_lieu_xanh.application.tag.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;
import com.nlu.tai_lieu_xanh.application.tag.mapper.TagMapper;
import com.nlu.tai_lieu_xanh.application.tag.service.TagService;
import com.nlu.tai_lieu_xanh.domain.tag.Tag;
import com.nlu.tai_lieu_xanh.domain.tag.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
  private final TagRepository tagRepository;
  private final TagMapper tagMapper;

  @Override
  public Tag findByName(String name) {
    return tagRepository.findByName(name).orElse(null);
  }

  @Override
  public List<Tag> getAllTags() {
    return tagRepository.findAll();
  }

  @Override
  public Tag save(String tagName) {
    var tag = Tag.create(tagName);
    return tagRepository.save(tag);
  }

  @Override
  public List<Tag> getOrSaveTags(List<String> tagNames) {
    var existingTags = tagRepository.findByNameIn(tagNames);
    var existingTagNames = existingTags.stream().map(Tag::getName).toList();
    var newTags = tagNames.stream()
        .filter(tagName -> !existingTagNames.contains(tagName))
        .map(this::save)
        .toList();
    existingTags.addAll(newTags);
    return existingTags;
  }

  @Override
  public List<TagResponse> findAll() {
    return tagMapper.toTagResponseList(tagRepository.findAll());
  }
}
