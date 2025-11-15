package com.nlu.tai_lieu_xanh.application.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.post.dto.response.AuthorResponse;
import com.nlu.tai_lieu_xanh.domain.comment.CommentStatus;
import com.nlu.tai_lieu_xanh.domain.user.User;

@Component
public class SharedMapper {
  public AuthorResponse toAuthor(User user) {
    if (user == null) {
      return null;
    }
    return new AuthorResponse(user.getId(), user.getFullName(), user.getAvatar());
  }

  public String formatDate(LocalDateTime createdAt) {
    if (createdAt == null) {
      return null;
    }
    var formatter = DateTimeFormatter.ofPattern("dd 'tháng' MM 'lúc' HH:mm", Locale.forLanguageTag("vi"));

    return createdAt.format(formatter);
  }

  public String toStatus(CommentStatus status) {
    if (status == null)
      return null;
    return status.toString();
  }
}
