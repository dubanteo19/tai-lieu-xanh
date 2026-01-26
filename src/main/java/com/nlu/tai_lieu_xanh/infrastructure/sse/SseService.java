package com.nlu.tai_lieu_xanh.infrastructure.sse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SseService {
  private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

  public SseEmitter subscribe() {
    var emitter = new SseEmitter(0L);
    emitters.add(emitter);
    emitter.onCompletion(() -> emitters.remove((emitter)));
    emitter.onTimeout(() -> emitters.remove((emitter)));
    emitter.onError((e) -> emitters.remove((emitter)));
    return emitter;
  }

  public void broadcast(Object data) {
    emitters.forEach(emitter -> {
      try {
        emitter.send(SseEmitter.event().name("global-message").data(data));
      } catch (IOException e) {
        emitters.remove(emitter);
        log.error(e.getMessage());
      }
    });
  }
}
