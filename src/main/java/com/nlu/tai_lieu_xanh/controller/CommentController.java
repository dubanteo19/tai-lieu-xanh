package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.request.comment.CommentCreateReq;
import com.nlu.tai_lieu_xanh.dto.request.comment.CommentDeleteReq;
import com.nlu.tai_lieu_xanh.dto.request.comment.CommentUpdateReq;
import com.nlu.tai_lieu_xanh.dto.response.comment.CommentRes;
import com.nlu.tai_lieu_xanh.service.CommentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {
  CommentService commentService;

  @DeleteMapping("{id}")
  public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
    commentService.deleteComment(id);
    return ResponseEntity.ok("Comment deleted");
  }

  @GetMapping
  public ResponseEntity<List<CommentRes>> getAllComments() {
    return ResponseEntity.ok(commentService.getAllComments());
  }

  @GetMapping("/post/{postId}")
  public ResponseEntity<List<CommentRes>> getComments(@PathVariable Integer postId) {
    return ResponseEntity.ok(commentService.getAllCommentsByPostId(postId));
  }

  @DeleteMapping("/post/{postId}")
  public ResponseEntity<String> deleteComment(@PathVariable Integer postId, @RequestBody CommentDeleteReq req) {
    commentService.deleteComment(postId, req);
    return ResponseEntity.ok("comment deleted");
  }

  @PutMapping("/post/{postId}")
  public ResponseEntity<CommentRes> updateComment(@PathVariable Integer postId, @RequestBody CommentUpdateReq req) {
    return ResponseEntity.ok(commentService.updateComment(postId, req));
  }

  @PostMapping("/post/{postId}")
  public ResponseEntity<CommentRes> saveComment(@PathVariable Integer postId,
      @RequestBody CommentCreateReq commentCreateReq) {
    return ResponseEntity.ok(commentService.saveComment(postId, commentCreateReq));
  }
}
