package com.nlu.tai_lieu_xanh.application.tag.mapper;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;
import com.nlu.tai_lieu_xanh.domain.tag.Tag;

@Component
public class TagMapper {

  public TagResponse toTagResponse(Tag tag) {
    if (tag == null) {
      return null;
    }
    return new TagResponse(tag.getId(), tag.getName());
  }

  public List<TagResponse> toTagResponseList(List<Tag> tags) {
    if (tags == null || tags.isEmpty()) {
      return List.of();
    }
    return tags.stream()
        .map(this::toTagResponse).toList();
  }
}
