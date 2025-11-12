package com.nlu.tai_lieu_xanh.application.user.dto.request;

public record UserUpdatePasswordReq(
    String password,
    String newPassword) {
}
