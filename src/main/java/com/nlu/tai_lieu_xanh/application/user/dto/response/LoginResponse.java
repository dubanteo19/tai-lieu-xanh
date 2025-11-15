package com.nlu.tai_lieu_xanh.application.user.dto.response;

public record LoginResponse(
    String status,
    String fullName,
    String bio,
    String accessToken,
    String refreshToken,
    Integer id,
    String email,
    String avatar) {
}
