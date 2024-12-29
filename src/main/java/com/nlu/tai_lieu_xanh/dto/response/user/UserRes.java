package com.nlu.tai_lieu_xanh.dto.response.user;

public record UserRes(
        Integer id,
        String email,
        String fullName,
        Integer comments,
        Integer posts,
        String status
) {
}
