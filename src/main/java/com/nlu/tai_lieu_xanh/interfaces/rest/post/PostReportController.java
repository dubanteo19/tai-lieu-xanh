package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.response.postReport.PostReportRes;
import com.nlu.tai_lieu_xanh.model.PostReport;
import com.nlu.tai_lieu_xanh.model.ReportReason;
import com.nlu.tai_lieu_xanh.model.ReportStatus;
import com.nlu.tai_lieu_xanh.service.PostReportService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
  public ResponseEntity<PostReport> reportPost(@RequestParam Integer postId,
      @RequestParam Integer userId,
      @RequestParam ReportReason reason) {
    PostReport report = postReportService.reportPost(postId, userId, reason);
    return ResponseEntity.ok(report);
  }

  // Admin endpoint to get reports for review
  @GetMapping("/admin/reports")
  public ResponseEntity<List<PostReportRes>> getReportsForAdmin(
      @RequestParam(defaultValue = "PENDING", required = false) ReportStatus status,
      @RequestParam(defaultValue = "0", required = false) int page,
      @RequestParam(defaultValue = "10", required = false) int size) {
    var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
    List<PostReportRes> reports = postReportService.getReportsForAdmin(pageable, status);
    return ResponseEntity.ok(reports);
  }

  // Admin endpoint to update report status
  @PostMapping("/admin/updateReportStatus")
  public ResponseEntity<String> updateReportStatus(@RequestParam Integer reportId,
      @RequestParam ReportStatus status) {
    postReportService.updateReportStatus(reportId, status);
    return ResponseEntity.ok("Report status updated");
  }
}
