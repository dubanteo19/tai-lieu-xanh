package com.nlu.tai_lieu_xanh.interfaces.rest.major;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.major.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.application.major.dto.request.MajorUpdateRequest;
import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.application.major.service.AdminMajorService;
import com.nlu.tai_lieu_xanh.utils.LocationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/majors")
public class AdminMajorController {

  private final AdminMajorService majorService;

  @GetMapping
  public ResponseEntity<MajorResponse> create(@RequestBody MajorCreateRequest request) {
    var response = majorService.save(request);
    var location = LocationUtils.buildLocation(response.id());
    return ResponseEntity.created(location).body(response);
  }

  @DeleteMapping("/{majorId}")
  public ResponseEntity<Void> deleteMajor(@PathVariable Long majorId) {
    majorService.delete(majorId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{majorId}")
  public ResponseEntity<MajorResponse> updateMajor(@PathVariable Long majorId,
      @RequestBody MajorUpdateRequest request) {
    return ResponseEntity.ok(majorService.update(majorId, request));
  }
}
