package com.nlu.tai_lieu_xanh.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.dto.request.MajorUpdateRequest;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorWithPostsRes;
import com.nlu.tai_lieu_xanh.dto.response.tag.TagWithPostsRes;
import com.nlu.tai_lieu_xanh.model.Major;
import com.nlu.tai_lieu_xanh.service.MajorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/majors")
public class AdminMajorController {
  private final MajorService majorService;

  @DeleteMapping("/{majorId}")
  public ResponseEntity<Boolean> deleteMajor(@PathVariable Integer majorId) {
    majorService.deleteMajor(majorId);
    return ResponseEntity.ok(true);
  }

  @PutMapping("/{majorId}")
  public ResponseEntity<Major> updateMajor(@PathVariable Integer majorId,
      @RequestBody MajorUpdateRequest request) {
    if (!majorId.equals(request.majorId())) {
      throw new IllegalArgumentException("Major id mismatch");
    }
    return ResponseEntity.ok(majorService.update(majorId, request));
  }

  @GetMapping("/tag-with-post")
  public ResponseEntity<List<TagWithPostsRes>> getTagWithPost() {
    return ResponseEntity.ok(majorService.getTagWithPost());
  }

  @GetMapping("/major-with-post")
  public ResponseEntity<List<MajorWithPostsRes>> getMajorWithPost() {
    return ResponseEntity.ok(majorService.getMajorWithPost());
  }

  @PostMapping
  public ResponseEntity<Major> createMajor(@RequestBody MajorCreateRequest request) {
    return ResponseEntity.ok(majorService.save(request));
  }
}
