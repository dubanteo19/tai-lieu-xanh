package com.nlu.tai_lieu_xanh.application.dashboard.dto.reponose;

public record DashboardInfoRes(
    Long totalPosts,
    Long totalUsers,
    Long totalComments,
    Long totalDownloads) {
}
