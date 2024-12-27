package com.nlu.tai_lieu_xanh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlu.tai_lieu_xanh.dto.request.post.PostCreateRequest;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorWithPostsRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostDetailRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.model.PostStatus;
import com.nlu.tai_lieu_xanh.service.MajorService;
import com.nlu.tai_lieu_xanh.service.PostService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PostController {
    PostService postService;
    MajorService majorService;

    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC
                        , "createdDate")
        );
        return ResponseEntity.ok(postService.findAllPost(pageable));
    }

    @GetMapping("/review")
    public ResponseEntity<List<PostResponse>> getAllReviewPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC
                        , "createdDate")
        );
        return ResponseEntity.ok(postService.findAllReviewPost(pageable));
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<PostResponse>> getAllDeletedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC
                        , "createdDate")
        );
        return ResponseEntity.ok(postService.findAllDeletedPost(pageable));
    }

    @GetMapping("/published")
    public ResponseEntity<List<PostResponse>> getAllPublishedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC
                        , "createdDate")
        );
        return ResponseEntity.ok(postService.findPublishedPosts(pageable));
    }

    @GetMapping("/hot-majors")
    public ResponseEntity<List<MajorWithPostsRes>> getHotMajorsWithPosts() {
        return ResponseEntity.ok(majorService.findHotMajorsWithPosts());
    }

    @GetMapping("/new")
    public ResponseEntity<List<PostResponse>> getNewPosts() {
        return ResponseEntity.ok(postService.findNewPosts());
    }

    @GetMapping("/related")
    public ResponseEntity<List<PostResponse>> getRelatedPosts(
            @RequestParam("postId") Integer postId
    ) {
        return ResponseEntity.ok(postService.findRelatedPosts(postId));
    }

    @GetMapping("/hot")
    public ResponseEntity<List<PostResponse>> getHotPosts() {
        return ResponseEntity.ok(postService.findHostPosts());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<StreamingResponseBody> downloadDocument(@PathVariable Integer id) {
        StreamingResponseBody streamingResponseBody = postService.download(id).a;
        String fileName = postService.download(id).b;
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"") // Force download
                .body(streamingResponseBody);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(
            @RequestParam(required = false) String fileType,
            @RequestParam(required = false) Integer majorId,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String direction) {
        List<PostResponse> files = postService.searchPosts(fileType, majorId, tags, keyword, sortBy, direction, page, size);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/id-list")
    public ResponseEntity<List<PostResponse>> getPostByIdList(@RequestParam String ids) {
        return ResponseEntity.ok(postService.getPostByIdList(ids));
    }

    //    @GetMapping("/{id}/report")
//    public ResponseEntity<String> reportPost(@PathVariable Integer id) {
//        return ResponseEntity.ok(postService.reportPost(id));
//    }
    @GetMapping("/{id}/detail")
    public ResponseEntity<PostDetailRes> getPostDetailById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.findPostDetailById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        postService.setPostStatus(id, PostStatus.DELETED);
        return ResponseEntity.ok("post deleted");
    }

    @DeleteMapping("{id}/deep-delete")
    public ResponseEntity<String> deepDeletePost(@PathVariable Integer id) {
        postService.delete(id);
        return ResponseEntity.ok("post deleted");
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectPost(@PathVariable Integer id) {
        postService.setPostStatus(id, PostStatus.REJECTED);
        return ResponseEntity.ok("post rejected");
    }

    @PostMapping("/{id}/ban")
    public ResponseEntity<String> banPost(@PathVariable Integer id) {
        postService.setPostStatus(id, PostStatus.BAN);
        return ResponseEntity.ok("post published");
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approvePost(@PathVariable Integer id) {
        postService.setPostStatus(id, PostStatus.PUBLISHED);
        return ResponseEntity.ok("post published");
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<String> viewPost(@PathVariable Integer id) {
        postService.viewPost(id);
        return ResponseEntity.ok("viewed post");
    }

    @PostMapping
    public ResponseEntity<PostDetailRes> createPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("majorId") Integer majorId,
            @RequestParam("tags") String tags, // JSON string of tags
            @RequestParam("authorId") Integer authorId
    ) throws JsonProcessingException {
        // Convert tags from JSON string to List<String>
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> tagList = objectMapper.readValue(tags, new TypeReference<List<String>>() {
        });

        PostCreateRequest postRequest = new PostCreateRequest(title, description, authorId, majorId, tagList);

        return ResponseEntity.ok(postService.save(postRequest, file));
    }
}
