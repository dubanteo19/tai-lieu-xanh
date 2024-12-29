package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.dto.response.user.*;
import com.nlu.tai_lieu_xanh.model.UserStatus;
import com.nlu.tai_lieu_xanh.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@AllArgsConstructor()
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @GetMapping("/{id}/info")
    public ResponseEntity<UserInfoRes> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findInfoById(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserRes>> findAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}/update-password")
    public ResponseEntity<UserInfoRes>
    updatePassword(@PathVariable Integer id, @RequestBody UserUpdatePasswordReq userUpdatePasswordReq) {
        return ResponseEntity.ok(userService.updatePassword(id, userUpdatePasswordReq));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UserInfoRes> updateStatus(
            @PathVariable Integer id,
            @RequestParam UserStatus status
    ) {
        return ResponseEntity.ok(userService.updateStatus(id,status));
    }

    @PutMapping("/{id}/info")
    public ResponseEntity<UserInfoRes> updateInfo(
            @PathVariable Integer id,
            @RequestParam("fullName") String fullName,
            @RequestParam("bio") String bio,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) {
        return ResponseEntity.ok(userService.updateInfo(id, fullName, bio, avatar));
    }

    @DeleteMapping("{id}/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id,
                                             @PathVariable Integer postId
                                             ) {
        userService.deletePost(id, postId);
        return ResponseEntity.ok("post deleted");
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostResponse>> getPosts(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getPostsById(id));
    }
}
