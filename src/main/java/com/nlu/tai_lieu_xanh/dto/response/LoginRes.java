package com.nlu.tai_lieu_xanh.dto.response;

public record LoginRes(
        String token,
        String refreshToken,
        Integer id,
        String email
) {
}