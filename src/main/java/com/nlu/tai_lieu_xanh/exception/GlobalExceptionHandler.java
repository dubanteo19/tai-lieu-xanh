package com.nlu.tai_lieu_xanh.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(FileStorageException.class)
  public ResponseEntity<Map<String, String>> handleFileStorageException(FileStorageException exception) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "File access failed");
    error.put("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

}
