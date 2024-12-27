package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.TagRequest;
import com.nlu.tai_lieu_xanh.dto.response.post.TagRes;
import com.nlu.tai_lieu_xanh.mapper.SharedConfig;
import com.nlu.tai_lieu_xanh.model.Tag;
import com.nlu.tai_lieu_xanh.repository.TagRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TagService {
    TagRepository tagRepository;

    public Tag findByName(String name) {
        return tagRepository.findByName(name).orElse(null);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag save(String tagName) {
        var tag = new Tag();
        tag.setName(tagName);
        return tagRepository.save(tag);
    }


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

    public List<TagRes> findAll() {
        return tagRepository.findAll().stream().map(SharedConfig::toTagRes).toList();
    }
}
