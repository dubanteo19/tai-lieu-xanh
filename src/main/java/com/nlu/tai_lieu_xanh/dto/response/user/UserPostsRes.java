package com.nlu.tai_lieu_xanh.dto.response.user;

import com.nlu.tai_lieu_xanh.model.Post;

import java.util.List;

public record UserPostsRes(
        List<Post> posts
) {
}
