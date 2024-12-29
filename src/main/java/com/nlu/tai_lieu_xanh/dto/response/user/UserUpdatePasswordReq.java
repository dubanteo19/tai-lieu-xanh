package com.nlu.tai_lieu_xanh.dto.response.user;

public record UserUpdatePasswordReq(
        String password,
        String newPassword
) {
}
