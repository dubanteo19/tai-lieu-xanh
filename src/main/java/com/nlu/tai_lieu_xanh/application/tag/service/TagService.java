package com.nlu.tai_lieu_xanh.application.tag.service;

import java.util.List;
import java.util.Set;

import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;
import com.nlu.tai_lieu_xanh.domain.tag.Tag;

public interface TagService {
  Tag findByName(String name);

  List<Tag> getAllTags();

  Tag save(String tagName);

  Set<Tag> getOrSaveTags(List<String> tagNames);

  List<TagResponse> findAll();
}
