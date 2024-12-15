package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.model.Tag;
import com.nlu.tai_lieu_xanh.repository.TagRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
