package com.nlu.tai_lieu_xanh.mapper;

import com.nlu.tai_lieu_xanh.dto.request.post.PostCreateRequest;
import com.nlu.tai_lieu_xanh.dto.response.post.MDocRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostDetailRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = SharedConfig.class)
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "tags", ignore = true)
    Post toPost(PostCreateRequest request);

    @Mapping(source = "author", target = "author", qualifiedByName = "toAuthor")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "toTags")
    @Mapping(source = "major", target = "major", qualifiedByName = "toMajor")
    @Mapping(source = "doc", target = " mdoc", qualifiedByName = "toMdocRes")
    @Mapping(source = "createdDate", target = "createdDate", qualifiedByName = "formatDate")
    PostDetailRes toPostDetailRes(Post post);

    @Mapping(source = "author", target = "author", qualifiedByName = "toAuthor")
    @Mapping(source = "createdDate", target = "createdDate", qualifiedByName = "formatDate")
    @Mapping(source = "major", target = "major", qualifiedByName = "toMajor")
    @Mapping(source = "postStatus", target = "status")
    @Mapping(source = "doc.downloads", target = "downloads")
    PostResponse toPostResponse(Post post);

    @Named("toMdocRes")
    default MDocRes toMdocRes(MDoc mDoc) {
        if (mDoc == null) {
            return null;
        }
        return new MDocRes(mDoc.getId(), mDoc.getFileName()
                , mDoc.getFileType().toString(), mDoc.getPages()
                , mDoc.getDownloads(), mDoc.getFileSize(), mDoc.getUrl());
    }

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

}
