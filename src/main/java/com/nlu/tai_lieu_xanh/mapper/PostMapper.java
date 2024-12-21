package com.nlu.tai_lieu_xanh.mapper;

import com.nlu.tai_lieu_xanh.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.dto.response.Author;
import com.nlu.tai_lieu_xanh.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "tags", ignore = true)
    Post toPost(PostCreateRequest request);

    @Mapping(source = "doc.url", target = "thumb")
    PostResponse toPostResponse(Post post);

    default Integer toComments(List<Comment> comments) {
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }

    default List<String> toTags(List<Tag> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }

    default String toMajor(Major major) {
        if (major == null) {
            return null;
        }
        return major.getName();
    }

    default Author toAuthor(User user) {
        if (user == null) {
            return null;
        }
        return new Author(user.getId(), user.getFullName(), user.getAvatar());
    }
}
