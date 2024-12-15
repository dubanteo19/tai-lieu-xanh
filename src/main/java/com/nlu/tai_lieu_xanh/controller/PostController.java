package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.model.Post;
import com.nlu.tai_lieu_xanh.service.PostService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PostController {
    PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostCreateRequest postRequest) {
        return ResponseEntity.ok(postService.save(postRequest));
    }
}
