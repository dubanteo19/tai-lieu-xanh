package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.response.dashboard.DashboardInfoRes;
import com.nlu.tai_lieu_xanh.service.DashboardService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DashboardController {

  DashboardService dashboardService;

  // Total posts
  @GetMapping("/info")
  public ResponseEntity<DashboardInfoRes> getDashboardInfo() {
    return ResponseEntity.ok(dashboardService.getDashboardInfo());
  }

  // Chart for last 10 days of post publishing
  @GetMapping("/posts-chart")
  public List<Map<String, Object>> getPostsChart() {
    return null;
    // return dashboardService.getPostsChart();
  }
}
