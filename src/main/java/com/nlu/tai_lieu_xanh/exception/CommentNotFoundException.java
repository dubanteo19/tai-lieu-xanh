package com.nlu.tai_lieu_xanh.exception;

public class CommentNotFoundException extends RuntimeException {
  public CommentNotFoundException() {
    super("Major not found");
  }
}
