package com.nlu.tai_lieu_xanh.controller;

package com.example.dashboard;

import com.nlu.tai_lieu_xanh.service.DashboardService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DashboardController {

    DashboardService dashboardService;

    // Total posts
    @GetMapping("/info")
    public ResponseEntity<List<Map<String, Object>>> getAll() {

    }

    // Chart for last 10 days of post publishing
    @GetMapping("/posts-chart")
    public List<Map<String, Object>> getPostsChart() {
        return dashboardService.getPostsChart();
    }
}
