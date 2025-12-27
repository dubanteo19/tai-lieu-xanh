package com.nlu.tai_lieu_xanh.interfaces.rest.post;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.post.report.dto.response.PostReportResponse;
import com.nlu.tai_lieu_xanh.application.post.report.serivce.PostReportService;
import com.nlu.tai_lieu_xanh.domain.report.PostReport;
import com.nlu.tai_lieu_xanh.domain.report.ReportReason;
import com.nlu.tai_lieu_xanh.domain.report.ReportStatus;

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
  public ResponseEntity<PostReport> reportPost(@RequestParam Long postId,
      @RequestParam Long userId,
      @RequestParam ReportReason reason) {
    PostReport report = postReportService.reportPost(postId, userId, reason);
    return ResponseEntity.ok(report);
  }

  // Admin endpoint to get reports for review
  @GetMapping("/admin/reports")
  public ResponseEntity<List<PostReportResponse>> getReportsForAdmin(
      @RequestParam(defaultValue = "PENDING", required = false) ReportStatus status,
      @RequestParam(defaultValue = "0", required = false) int page,
      @RequestParam(defaultValue = "10", required = false) int size) {
    var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
    List<PostReportResponse> reports = postReportService.getReportsForAdmin(pageable, status);
    return ResponseEntity.ok(reports);
  }

  // Admin endpoint to update report status
  @PostMapping("/admin/updateReportStatus")
  public ResponseEntity<String> updateReportStatus(@RequestParam Long reportId,
      @RequestParam ReportStatus status) {
    postReportService.updateReportStatus(reportId, status);
    return ResponseEntity.ok("Report status updated");
  }
}
