package com.nlu.tai_lieu_xanh.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.nlu.tai_lieu_xanh.service.FtpService;
import com.nlu.tai_lieu_xanh.service.MDocService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/documents")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class MDocController {
  FtpService ftpService;
  MDocService mDocService;

  @GetMapping("/download-thumb")
  public ResponseEntity<StreamingResponseBody> downloadThumb(@RequestParam("uri") String url) {
    String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8); // Decode the URL
    StreamingResponseBody streamingResponseBody = out -> {
      ftpService.downloadFile(decodedUrl, out);
    };
    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
        .body(streamingResponseBody);
  }

  @GetMapping("/download")
  public ResponseEntity<StreamingResponseBody> downloadDocument(@RequestParam("uri") String url) {
    String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8); // Decode the URL
    StreamingResponseBody streamingResponseBody = out -> {
      ftpService.downloadFile(decodedUrl, out);
    };
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
        .body(streamingResponseBody);
  }

}
