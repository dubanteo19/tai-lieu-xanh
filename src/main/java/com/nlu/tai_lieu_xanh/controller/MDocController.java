package com.nlu.tai_lieu_xanh.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.service.MDocService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class MDocController {
  private final MDocService mDocService;

  @GetMapping("{id}/previews")
  public ResponseEntity<List<String>> getPreivewUrls(@PathVariable int id) {
    List<String> previewUrls = mDocService.getPreivewUrls(id);
    return ResponseEntity.ok(previewUrls);
  }
}
