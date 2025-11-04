package com.nlu.tai_lieu_xanh.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.dto.response.postReport.PostReportRes;
import com.nlu.tai_lieu_xanh.mapper.SharedConfig;
import com.nlu.tai_lieu_xanh.model.PostReport;
import com.nlu.tai_lieu_xanh.model.ReportReason;
import com.nlu.tai_lieu_xanh.model.ReportStatus;
import com.nlu.tai_lieu_xanh.repository.PostReportRepository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PostReportService {

  PostReportRepository postReportRepository;
  PostService postService;
  UserService userService;
  NotificationService notificationService;

  // Create a new report
  public PostReport reportPost(Integer postId, Integer userId, ReportReason reason) {
    var post = postService.findById(postId);
    var user = userService.findById(userId);
    PostReport report = new PostReport();
    report.setPost(post);
    report.setUser(user);
    report.setReason(reason);
    report.setStatus(ReportStatus.PENDING);
    notificationService.createNotification(userId, "Báo cáo bài viết %s với lý do %s của bạn đang được xem xét"
        .formatted(post.getTitle(), ReportReason.toVNReason(reason)));
    return postReportRepository.save(report);
  }

  // Integer id,
  // ReportReason reason,
  // String postTitle,
  // Integer postId,
  // Integer authorId,
  // Integer userId,
  // Integer fullName,
  // String createdDate
  public PostReportRes toPostReportRes(PostReport postReport) {
    return new PostReportRes(
        postReport.getId(),
        postReport.getReason(),
        postReport.getPost().getTitle(),
        postReport.getPost().getId(),
        postReport.getPost().getAuthor().getId(),
        postReport.getUser().getId(),
        postReport.getUser().getFullName(),
        SharedConfig.formatDate(postReport.getCreatedDate()),
        postReport.getStatus());
  }

  // Get reports for admin review
  public List<PostReportRes> getReportsForAdmin(Pageable pageable, ReportStatus reportStatus) {
    return postReportRepository
        .findByStatus(reportStatus, pageable)
        .stream()
        .map(this::toPostReportRes)
        .toList();
  }

  // Admin action to approve or reject the report
  public void updateReportStatus(Integer reportId, ReportStatus status) {
    PostReport report = postReportRepository.findById(reportId)
        .orElseThrow(() -> new RuntimeException("Report not found"));
    report.setStatus(status);
    postReportRepository.save(report);
  }
}
