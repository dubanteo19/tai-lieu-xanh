package com.nlu.tai_lieu_xanh.application.post.report.serivce;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nlu.tai_lieu_xanh.application.post.report.dto.response.PostReportResponse;
import com.nlu.tai_lieu_xanh.domain.report.ReportReason;
import com.nlu.tai_lieu_xanh.domain.report.ReportStatus;

public interface PostReportService {
  void reportPost(Long postId, ReportReason reason);

  void updateReportStatus(Long reportId, ReportStatus status);

  List<PostReportResponse> findReports(Pageable pageable, ReportStatus reportStatus);
}
