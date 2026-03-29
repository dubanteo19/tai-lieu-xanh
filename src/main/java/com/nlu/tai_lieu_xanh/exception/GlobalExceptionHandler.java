package com.nlu.tai_lieu_xanh.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGlobalException(Exception exception) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "Internal error");
    error.put("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(FileStorageException.class)
  public ResponseEntity<Map<String, String>> handleFileStorageException(
      FileStorageException exception) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "File access failed");
    error.put("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(
      UsernameNotFoundException exception) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "User not found");
    error.put("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
