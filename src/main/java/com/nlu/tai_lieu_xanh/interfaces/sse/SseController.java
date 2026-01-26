package com.nlu.tai_lieu_xanh.interfaces.sse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.nlu.tai_lieu_xanh.infrastructure.sse.SseService;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/sse")
@Log4j2
@RequiredArgsConstructor
public class SseController {
  private final SseService sseService;

  @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter subscribe() {
    return sseService.subscribe();
  }

  @PostMapping("/publish")
  public ResponseEntity<Void> publish(@RequestBody String message) {
    sseService.broadcast(message);
    log.info("recieve message " + message);
    return ResponseEntity.ok().build();
  }

}
