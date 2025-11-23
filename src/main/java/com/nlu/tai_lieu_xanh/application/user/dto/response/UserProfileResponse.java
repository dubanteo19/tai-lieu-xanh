package com.nlu.tai_lieu_xanh.application.user.dto.response;

public record UserProfileResponse(
    String fullName,
    String bio,
    String email,
    String avatar,
    int friendCounts,
    int postCounts) {
}
