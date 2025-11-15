package com.nlu.tai_lieu_xanh.interfaces.rest.major;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.application.major.service.MajorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/majors")
public class MajorController {

  private final MajorService majorService;

  @GetMapping
  public ResponseEntity<List<MajorResponse>> getAllMajors() {
    return ResponseEntity.ok(majorService.findAll());
  }

  @GetMapping("/{majorName}")
  public ResponseEntity<List<MajorResponse>> searchMajorsByName(@PathVariable String majorName) {
    return ResponseEntity.ok(majorService.searchMajorsByName(majorName));
  }

}
