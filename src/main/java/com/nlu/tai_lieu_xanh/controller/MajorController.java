package com.nlu.tai_lieu_xanh.controller;


import com.nlu.tai_lieu_xanh.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.dto.request.MajorUpdateRequest;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorRes;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorWithPostsRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.dto.response.tag.TagWithPostsRes;
import com.nlu.tai_lieu_xanh.model.Major;
import com.nlu.tai_lieu_xanh.service.MajorService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("/api/v1/majors")
public class MajorController {
    MajorService majorService;

    @GetMapping
    public ResponseEntity<List<MajorRes>> getAllMajors() {
        return ResponseEntity.ok(majorService.findAll());
    }

    @GetMapping("/{majorName}")
    public ResponseEntity<List<Major>> searchMajorsByName(@PathVariable String majorName) {
        return ResponseEntity.ok(majorService.searchByName(majorName));
    }

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
