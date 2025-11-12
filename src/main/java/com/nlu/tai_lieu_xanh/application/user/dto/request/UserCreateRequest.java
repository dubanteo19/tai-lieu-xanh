package com.nlu.tai_lieu_xanh.application.user.dto.request;

public record UserCreateRequest(
    String email,
    String fullName,
    String password) {
}
