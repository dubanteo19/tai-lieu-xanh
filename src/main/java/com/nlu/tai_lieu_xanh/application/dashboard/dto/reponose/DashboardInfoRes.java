package com.nlu.tai_lieu_xanh.dto.response.dashboard;

public record DashboardInfoRes(
        Long totalPosts,
        Long totalUsers,
        Long totalComments,
        Long totalDownloads
) {
}
