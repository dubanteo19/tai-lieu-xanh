package com.nlu.tai_lieu_xanh.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.dto.response.post.MajorRes;
import com.nlu.tai_lieu_xanh.model.Major;
import com.nlu.tai_lieu_xanh.service.MajorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/majors")
public class MajorController {
  private final MajorService majorService;

  @GetMapping
  public ResponseEntity<List<MajorRes>> getAllMajors() {
    return ResponseEntity.ok(majorService.findAll());
  }

  @GetMapping("/{majorName}")
  public ResponseEntity<List<Major>> searchMajorsByName(@PathVariable String majorName) {
    return ResponseEntity.ok(majorService.searchByName(majorName));
  }

}
