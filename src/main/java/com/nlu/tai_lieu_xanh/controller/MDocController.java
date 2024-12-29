package com.nlu.tai_lieu_xanh.controller;

import com.nlu.tai_lieu_xanh.dto.response.post.MDocRes;
import com.nlu.tai_lieu_xanh.service.FtpService;
import com.nlu.tai_lieu_xanh.service.MDocService;
import jakarta.mail.Multipart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/documents")
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
