package com.nlu.tai_lieu_xanh.application.post.report.dto.response;

import com.nlu.tai_lieu_xanh.domain.report.ReportReason;
import com.nlu.tai_lieu_xanh.domain.report.ReportStatus;

public record PostReportResponse(
    Long id,
    ReportReason reason,
    String postTitle,
    Long postId,
    Long authorId,
    Long userId,
    String fullName,
    String createdDate,
    ReportStatus status) {
}
