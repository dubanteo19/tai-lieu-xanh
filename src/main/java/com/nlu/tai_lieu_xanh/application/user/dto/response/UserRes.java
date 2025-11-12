package com.nlu.tai_lieu_xanh.application.user.dto.response;

public record UserRes(
    Integer id,
    String email,
    String fullName,
    Integer comments,
    Integer posts,
    String status) {
}
