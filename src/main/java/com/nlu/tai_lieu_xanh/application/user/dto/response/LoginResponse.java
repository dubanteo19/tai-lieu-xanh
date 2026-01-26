package com.nlu.tai_lieu_xanh.application.user.dto.response;

public record LoginResponse(
    String accessToken,
    UserSummary user) {
}
