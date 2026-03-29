package com.nlu.tai_lieu_xanh.interfaces.rest.user;

import com.nlu.tai_lieu_xanh.application.user.dto.response.UserProfileResponse;
import com.nlu.tai_lieu_xanh.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("{id}/info")
  public ResponseEntity<UserProfileResponse> findInfoById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findInfoById(id));
  }
}
