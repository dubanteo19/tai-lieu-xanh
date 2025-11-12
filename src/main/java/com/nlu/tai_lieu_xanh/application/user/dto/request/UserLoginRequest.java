package com.nlu.tai_lieu_xanh.application.user.dto.request;

public record UserLoginRequest(
        String email,
        String password
) {
}
