package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.response.post.TagRes;
import com.nlu.tai_lieu_xanh.service.TagService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TagController {
    TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagRes>> getAllTags() {
        return ResponseEntity.ok(tagService.findAll());
    }
}
