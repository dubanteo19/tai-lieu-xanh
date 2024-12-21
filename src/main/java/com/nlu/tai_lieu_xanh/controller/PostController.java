package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.model.Post;
import com.nlu.tai_lieu_xanh.service.FtpService;
import com.nlu.tai_lieu_xanh.service.PostService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PostController {
    PostService postService;
    FtpService ftpService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{postId}/download")
    public ResponseEntity<StreamingResponseBody> downloadDocument(@PathVariable Integer postId) {
        var doc = postService.findDocumentById(postId);
        var docUrl = doc.getUrl();
        StreamingResponseBody streamingResponseBody = out -> {
            ftpService.downloadFile(docUrl, out);
        };
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + doc.getFileName())
                .body(streamingResponseBody);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostCreateRequest postRequest) {
        return ResponseEntity.ok(postService.save(postRequest));
    }
}
