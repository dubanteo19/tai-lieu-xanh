package com.nlu.tai_lieu_xanh.interfaces.rest.post;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.service.AdminPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/posts")
@RequiredArgsConstructor
public class AdminPostController {

  private final AdminPostService adminPostService;

  @GetMapping("/review")
  public ResponseEntity<List<PostResponse>> getAllReviewPosts(
      @RequestParam(defaultValue = "0") int cursor) {
    var sort = Sort.by(Sort.Direction.DESC, "createdDate");
    throw new UnsupportedOperationException();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> markAsDeleted(@PathVariable Long id) {
    adminPostService.delete(id);
    return ResponseEntity.ok("post deleted");
  }

  @PostMapping("/{id}/reject")
  public ResponseEntity<String> rejectPost(@PathVariable Long id,
      @RequestParam String reason) {
    adminPostService.rejectPost(id, reason);
    return ResponseEntity.ok("post rejected");
  }

  @PostMapping("/{id}/approve")
  public ResponseEntity<String> approvePost(@PathVariable Long id) {
    adminPostService.approvePost(id);
    return ResponseEntity.ok("post published");
  }
}
