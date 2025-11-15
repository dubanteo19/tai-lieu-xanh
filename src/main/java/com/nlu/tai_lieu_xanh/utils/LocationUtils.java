package com.nlu.tai_lieu_xanh.utils;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * LocationUtils
 */
public final class LocationUtils {
  private LocationUtils() {
  }

  public static URI buildLocation(Object id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }
}
