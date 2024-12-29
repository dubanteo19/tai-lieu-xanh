package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.model.PostReport;
import com.nlu.tai_lieu_xanh.model.ReportStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport, Integer> {
    List<PostReport> findByStatus(ReportStatus status, Pageable pageable);
}
