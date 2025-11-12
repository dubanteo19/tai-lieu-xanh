package com.nlu.tai_lieu_xanh.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlu.tai_lieu_xanh.domain.post.PostStatus;
import com.nlu.tai_lieu_xanh.dto.request.post.PostCreateRequest;
import com.nlu.tai_lieu_xanh.dto.response.mdoc.PresignedUrlRes;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorWithPostsRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostDetailRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.service.MajorService;
import com.nlu.tai_lieu_xanh.service.PostService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PostController {
  PostService postService;
  MajorService majorService;

  @GetMapping()
  public ResponseEntity<List<PostResponse>> getAllPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "6") int size) {
    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.DESC, "createdDate"));
    return ResponseEntity.ok(postService.findAllPost(pageable));
  }

  @GetMapping("/review")
  public ResponseEntity<List<PostResponse>> getAllReviewPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "6") int size) {
    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.DESC, "createdDate"));
    return ResponseEntity.ok(postService.findAllReviewPost(pageable));
  }

  @GetMapping("/deleted")
  public ResponseEntity<List<PostResponse>> getAllDeletedPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "6") int size) {
    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.DESC, "createdDate"));
    return ResponseEntity.ok(postService.findAllDeletedPost(pageable));
  }

  @GetMapping("/published")
  public ResponseEntity<List<PostResponse>> getAllPublishedPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "6") int size) {
    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.DESC, "createdDate"));
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
      @RequestParam("postId") Integer postId) {
    return ResponseEntity.ok(postService.findRelatedPosts(postId));
  }

  @GetMapping("/hot")
  public ResponseEntity<List<PostResponse>> getHotPosts() {
    return ResponseEntity.ok(postService.findHotPosts());
  }

  @GetMapping("/{id}/download")
  public ResponseEntity<PresignedUrlRes> downloadDocument(@PathVariable Integer id) {
    var presignedUrl = postService.download(id);
    return ResponseEntity.ok(presignedUrl);
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
  public ResponseEntity<String> rejectPost(@PathVariable Integer id,
      @RequestParam String reason) {
    postService.rejectPost(id, PostStatus.REJECTED, reason);
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
      @RequestParam("authorId") Integer authorId) throws JsonProcessingException {
    // Convert tags from JSON string to List<String>
    ObjectMapper objectMapper = new ObjectMapper();
    List<String> tagList = objectMapper.readValue(tags, new TypeReference<List<String>>() {
    });

    PostCreateRequest postRequest = new PostCreateRequest(title, description, authorId, majorId, tagList);

    return ResponseEntity.ok(postService.save(postRequest, file));
  }
}
