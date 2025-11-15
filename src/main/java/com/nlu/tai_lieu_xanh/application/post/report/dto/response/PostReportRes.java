package com.nlu.tai_lieu_xanh.dto.response.postReport;

import com.nlu.tai_lieu_xanh.domain.report.ReportReason;
import com.nlu.tai_lieu_xanh.domain.report.ReportStatus;

public record PostReportRes(
    Integer id,
    ReportReason reason,
    String postTitle,
    Integer postId,
    Integer authorId,
    Integer userId,
    String fullName,
    String createdDate,
    ReportStatus status) {
}
