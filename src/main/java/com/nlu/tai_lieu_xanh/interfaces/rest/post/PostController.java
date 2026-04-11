package com.nlu.tai_lieu_xanh.interfaces.rest.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentResponse;
import com.nlu.tai_lieu_xanh.application.comment.service.CommentService;
import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.PresignedUrlRes;
import com.nlu.tai_lieu_xanh.application.mdoc.service.MDocService;
import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.service.PostService;
import com.nlu.tai_lieu_xanh.application.shared.response.CursorResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;
  private final CommentService commentService;
  private final MDocService mDocService;

  @GetMapping
  public ResponseEntity<CursorResponse<PostResponse>> getAllPublishedPosts(
      @RequestParam(required = false) LocalDateTime cursor) {
    return ResponseEntity.ok(postService.findPublishedPosts(cursor));
  }

  @GetMapping("/{id}/download")
  public ResponseEntity<PresignedUrlRes> downloadDocument(@PathVariable Long id) {
    var presignedUrl = mDocService.download(id);
    return ResponseEntity.ok(presignedUrl);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDetailResponse> getPostDetailById(@PathVariable Long id) {
    return ResponseEntity.ok(postService.findPostDetail(id));
  }

  @PostMapping("/{id}/view")
  public ResponseEntity<String> viewPost(@PathVariable Long id) {
    postService.viewPost(id);
    return ResponseEntity.ok("viewed post");
  }

  @PostMapping
  public ResponseEntity<PostResponse> createPost(
      @RequestParam("file") MultipartFile file,
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("majorId") Long majorId,
      @RequestParam("tags") String tags)
      throws JsonProcessingException {
    // Convert tags from JSON string to List<String>
    ObjectMapper objectMapper = new ObjectMapper();
    List<String> tagList = objectMapper.readValue(tags, new TypeReference<List<String>>() {});

    var postRequest = new PostCreateRequest(title, description, majorId, tagList);

    return ResponseEntity.ok(postService.create(postRequest, file));
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
    return ResponseEntity.ok(commentService.getAllByPostId(postId));
  }
}
