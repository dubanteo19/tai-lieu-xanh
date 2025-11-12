package com.nlu.tai_lieu_xanh.application.user.dto.response;

public record LoginRes(
    String status,
    String fullName,
    String bio,
    String accessToken,
    String refreshToken,
    Integer id,
    String email,
    String avatar
) {
}
