package com.nlu.tai_lieu_xanh.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentCreateRequest;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentDeleteReq;
import com.nlu.tai_lieu_xanh.application.comment.dto.request.CommentUpdateRequest;
import com.nlu.tai_lieu_xanh.application.comment.dto.response.CommentResponse;
import com.nlu.tai_lieu_xanh.application.comment.service.CommentService;
import com.nlu.tai_lieu_xanh.utils.LocationUtils;

@RestController
@RequestMapping("/comments")
public class CommentController {
  private final CommentService commentService;

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
    commentService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/update")
  public ResponseEntity<CommentResponse> updateComment(@PathVariable Integer postId,
      @RequestBody CommentUpdateRequest request) {
    return ResponseEntity.ok(commentService.update(request));
  }

  @PostMapping
  public ResponseEntity<CommentResponse> saveComment(
      @RequestBody CommentCreateRequest request) {
    var response = commentService.save(request);
    var location = LocationUtils.buildLocation(response.id());
    return ResponseEntity.created(location).build();
  }
}
