package com.nlu.tai_lieu_xanh.interfaces.rest.post;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.post.report.serivce.PostReportService;
import com.nlu.tai_lieu_xanh.domain.report.ReportReason;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/reports")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostReportController {

  PostReportService postReportService;

  // Get available report reasons
  @GetMapping("/report-reasons")
  public ResponseEntity<List<ReportReason>> getReportReasons() {
    return ResponseEntity.ok(Arrays.asList(ReportReason.values()));
  }

  // Allow users to report a post with a predefined reason
  @PostMapping("/report-post")
  public ResponseEntity<String> reportPost(@RequestParam Long postId,
      @RequestParam Long userId,
      @RequestParam ReportReason reason) {
    postReportService.reportPost(postId, reason);
    return ResponseEntity.ok("Post reported");
  }

}
