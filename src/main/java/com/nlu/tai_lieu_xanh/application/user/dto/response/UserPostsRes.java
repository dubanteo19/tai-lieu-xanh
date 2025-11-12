package com.nlu.tai_lieu_xanh.application.user.dto.response;

import java.util.List;

import com.nlu.tai_lieu_xanh.domain.post.Post;

public record UserPostsRes(
    List<Post> posts) {
}
