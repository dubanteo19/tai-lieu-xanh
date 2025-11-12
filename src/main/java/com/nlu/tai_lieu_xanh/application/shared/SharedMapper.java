package com.nlu.tai_lieu_xanh.application.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.domain.comment.CommentStatus;
import com.nlu.tai_lieu_xanh.domain.major.Major;
import com.nlu.tai_lieu_xanh.domain.tag.Tag;
import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.dto.response.post.Author;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorRes;
import com.nlu.tai_lieu_xanh.dto.response.post.TagRes;

@Component
public class SharedMapper {
  public Author toAuthor(User user) {
    if (user == null) {
      return null;
    }
    return new Author(user.getId(), user.getFullName(), user.getAvatar());
  }

  public List<TagRes> toTags(List<Tag> tags) {
    if (tags == null) {
      return null;
    }
    return tags.stream()
        .map(this::toTagRes).toList();
  }

  public TagRes toTagRes(Tag tag) {
    if (tag == null) {
      return null;
    }
    return new TagRes(tag.getId(), tag.getName());
  }

  public MajorRes toMajorRes(Major major) {
    if (major == null) {
      return null;
    }
    return new MajorRes(major.getId(), major.getName());
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
