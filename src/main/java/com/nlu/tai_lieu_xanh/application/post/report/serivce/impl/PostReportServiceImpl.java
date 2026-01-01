package com.nlu.tai_lieu_xanh.application.post.report.serivce.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.post.report.dto.response.PostReportResponse;
import com.nlu.tai_lieu_xanh.application.post.report.serivce.PostReportService;
import com.nlu.tai_lieu_xanh.domain.report.ReportReason;
import com.nlu.tai_lieu_xanh.domain.report.ReportStatus;

@Service
public class PostReportServiceImpl implements PostReportService {

  @Override
  public void reportPost(Long postId, ReportReason reason) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'reportPost'");
  }

  @Override
  public void updateReportStatus(Long reportId, ReportStatus status) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateReportStatus'");
  }

  @Override
  public List<PostReportResponse> findReports(Pageable pageable, ReportStatus reportStatus) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findReports'");
  }

}
