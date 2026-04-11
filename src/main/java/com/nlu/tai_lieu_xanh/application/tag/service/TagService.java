package com.nlu.tai_lieu_xanh.application.tag.service;

import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;
import com.nlu.tai_lieu_xanh.domain.tag.Tag;
import java.util.List;

public interface TagService {
  Tag findByName(String name);

  List<Tag> getAllTags();

  Tag save(String tagName);

  List<Tag> getOrSaveTags(List<String> tagNames);

  List<TagResponse> findAll();
}
