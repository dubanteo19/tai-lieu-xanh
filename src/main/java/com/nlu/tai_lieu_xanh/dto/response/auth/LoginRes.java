package com.nlu.tai_lieu_xanh.dto.response.auth;

public record LoginRes(
        String status,
        String fullName,
        String bio,
        String token,
        String refreshToken,
        Integer id,
        String email,
        String avatar
) {
}