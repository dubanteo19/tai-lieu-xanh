package com.nlu.tai_lieu_xanh.application.shared.response;

import java.time.LocalDateTime;
import java.util.List;

public record CursorResponse<T>(
    List<T> items,
    LocalDateTime nextCursor,
    boolean hasNext) {

}
