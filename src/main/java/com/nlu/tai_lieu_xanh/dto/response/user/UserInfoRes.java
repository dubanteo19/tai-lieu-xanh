package com.nlu.tai_lieu_xanh.dto.response.user;

public record UserInfoRes(
        String fullName,
        String bio,
        String email,
        String avatar,
        Integer friends,
        Integer posts
) {
}
