package com.nlu.tai_lieu_xanh.mapper;

import com.nlu.tai_lieu_xanh.dto.response.post.Author;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorRes;
import com.nlu.tai_lieu_xanh.dto.response.post.TagRes;
import com.nlu.tai_lieu_xanh.model.Major;
import com.nlu.tai_lieu_xanh.model.Tag;
import com.nlu.tai_lieu_xanh.model.User;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public interface SharedConfig {
    @Named("toAuthor")
    static Author toAuthor(User user) {
        if (user == null) {
            return null;
        }
        return new Author(user.getId(), user.getFullName(), user.getAvatar());
    }

    @Named("toTags")
    static List<TagRes> toTags(List<Tag> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream()
                .map(SharedConfig::toTagRes).toList();
    }

    @Named("toTag")
    static TagRes toTagRes(Tag tag) {
        if (tag == null) {
            return null;
        }
        return new TagRes(tag.getId(), tag.getName());
    }

    @Named("toMajor")
    static MajorRes toMajorRes(Major major) {
        if (major == null) {
            return null;
        }
        return new MajorRes(major.getId(), major.getName());
    }

    @Named("formatDate")
    static String formatDate(LocalDateTime createdAt) {
        if (createdAt == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'tháng' MM 'lúc' HH:mm", Locale.forLanguageTag("vi"));

        return createdAt.format(formatter);
    }
}
