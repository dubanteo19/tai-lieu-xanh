
package com.nlu.tai_lieu_xanh.exception;

public class FileUploadException extends RuntimeException {
  public FileUploadException(String message) {
    super(message);
  }

  public FileUploadException(String message, Throwable cause) {
    super(message, cause);
  }
}
