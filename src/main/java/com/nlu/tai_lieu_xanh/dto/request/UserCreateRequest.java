package com.nlu.tai_lieu_xanh.dto.request;

public record UserCreateRequest(
    String email,
    String fullName,
    String password) {
}
