package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.response.dashboard.DashboardInfoRes;
import com.nlu.tai_lieu_xanh.dto.response.postReport.PostReportRes;
import com.nlu.tai_lieu_xanh.repository.CommentRepository;
import com.nlu.tai_lieu_xanh.repository.MDocRepository;
import com.nlu.tai_lieu_xanh.repository.PostReportRepository;
import com.nlu.tai_lieu_xanh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DashboardService {
    PostReportRepository postReportRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;
    MDocRepository mDocRepository;

    public DashboardInfoRes getDashboardInfo() {
        var totalPosts = postReportRepository.count();
        var totalUsers = userRepository.count();
        var totalComments = commentRepository.count();
        var totalDownloads = mDocRepository.totalDownload();
        totalDownloads = (totalDownloads == null) ? 0 : totalDownloads;
        return new DashboardInfoRes(totalPosts, totalUsers, totalComments, totalDownloads);
    }
}
