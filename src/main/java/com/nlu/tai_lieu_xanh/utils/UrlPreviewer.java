package com.nlu.tai_lieu_xanh.utils;

import java.util.List;
import java.util.stream.IntStream;

public final class UrlPreviewer {
  private final static String BASE_URL = "http://localhost:9000";
  private final static String EXT = ".webp";

  private UrlPreviewer() {
  };

  public static String generateThumbnail(Long mdocId) {
    return BASE_URL + "/previews/doc-" + mdocId + "/page-1" + EXT;
  }

  public static List<String> generate(Long mdocId, int previewCount) {
    if (previewCount <= 0) {
      return List.of();
    }
    String prefix = BASE_URL + "/previews/doc-" + mdocId + "/page-";
    String EXTENSION = ".webp";
    return IntStream
        .rangeClosed(1, previewCount)
        .mapToObj(i -> prefix + i + EXTENSION)
        .toList();
  }
}
