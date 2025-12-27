package com.nlu.tai_lieu_xanh.application.user.dto.response;

public record UserSummary(
    Long id,
    String email,
    String fullName,
    String avatarUrl) {
}
