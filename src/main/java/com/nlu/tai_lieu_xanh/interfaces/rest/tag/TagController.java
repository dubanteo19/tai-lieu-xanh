package com.nlu.tai_lieu_xanh.interfaces.rest.tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;
import com.nlu.tai_lieu_xanh.application.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;

  @GetMapping
  public ResponseEntity<List<TagResponse>> getAllTags() {
    return ResponseEntity.ok(tagService.findAll());
  }
}
