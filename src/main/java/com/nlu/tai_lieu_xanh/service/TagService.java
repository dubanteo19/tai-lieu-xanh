package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.TagRequest;
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


    public List<Tag> getOrSaveTags(List<TagRequest> tags) {
        List<String> tagNames = tags.stream().map(TagRequest::tagName).collect(Collectors.toList());
        var existingTags = tagRepository.findByNameIn(tagNames);
        System.out.printf("Existing tags: %s", existingTags);
        var existingTagNames = existingTags.stream().map(Tag::getName).toList();
        System.out.printf("Existing tagNames: %s", existingTagNames);
        var newTags = tagNames.stream()
                .filter(tagName -> !existingTagNames.contains(tagName))
                .map(this::save)
                .toList();
        System.out.printf("New tags: %s", newTags);
        existingTags.addAll(newTags);
        return existingTags;
    }
}
