package com.nlu.tai_lieu_xanh.interfaces.rest.post;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentResponse;
import com.nlu.tai_lieu_xanh.application.comment.service.CommentService;
import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.PresignedUrlRes;
import com.nlu.tai_lieu_xanh.application.mdoc.service.MDocService;
import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;
  private final CommentService commentService;
  private final MDocService mDocService;

  @GetMapping
  public ResponseEntity<List<PostResponse>> getAllPublishedPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "6") int size) {
    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.DESC, "createdDate"));
    return ResponseEntity.ok(postService.findPublishedPosts(pageable));
  }

  @GetMapping("/{id}/download")
  public ResponseEntity<PresignedUrlRes> downloadDocument(@PathVariable Long id) {
    var presignedUrl = mDocService.download(id);
    return ResponseEntity.ok(presignedUrl);
  }

  /*
   * @GetMapping("/search")
   * public ResponseEntity<List<PostResponse>> searchPosts(
   * 
   * @RequestParam(required = false) String fileType,
   * 
   * @RequestParam(required = false) Long majorId,
   * 
   * @RequestParam(required = false) List<String> tags,
   * 
   * @RequestParam(required = false) String keyword,
   * 
   * @RequestParam(defaultValue = "0") int page,
   * 
   * @RequestParam(defaultValue = "10") int size,
   * 
   * @RequestParam(required = false, defaultValue = "createdDate") String sortBy,
   * 
   * @RequestParam(required = false, defaultValue = "DESC") String direction) {
   * List<PostResponse> files = postService.searchPosts(fileType, majorId, tags,
   * keyword, sortBy, direction, page, size);
   * return ResponseEntity.ok(files);
   * }
   */

  @GetMapping("/{id}/detail")
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
      @RequestParam("tags") String tags, // JSON string of tags
      @RequestParam("authorId") Long authorId) throws JsonProcessingException {
    // Convert tags from JSON string to List<String>
    ObjectMapper objectMapper = new ObjectMapper();
    List<String> tagList = objectMapper.readValue(tags, new TypeReference<List<String>>() {
    });

    var postRequest = new PostCreateRequest(title, description, majorId, tagList);

    return ResponseEntity.ok(postService.create(postRequest, file));
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
    return ResponseEntity.ok(commentService.getAllByPostId(postId));
  }

}
