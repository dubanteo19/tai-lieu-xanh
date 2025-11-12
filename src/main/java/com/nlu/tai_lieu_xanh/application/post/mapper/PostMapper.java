package com.nlu.tai_lieu_xanh.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.shared.SharedMapper;
import com.nlu.tai_lieu_xanh.domain.major.Major;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.dto.response.post.MDocRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostDetailRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostMapper {
  private final SharedMapper sharedMapper;

  Post toPost(PostCreateRequest request) {
  };

  PostDetailRes toPostDetailRes(Post post) {
    var author = sharedMapper.toAuthor(post.getAuthor());
    var major = sharedMapper.toMajorRes(post.getMajor());
    var mdoc = new 
  return new PostDetailRes(
      post.getId(),
      post.getTitle(),
      post.getDescription(),
      mdoc,
      author,
      createdDate,
      major,
      tags)
  };

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
    return new MDocRes(mDoc.getId(), mDoc.getFileName(), mDoc.getFileType().toString(), mDoc.getPages(),
        mDoc.getDownloads(), mDoc.getFileSize(), mDoc.getUrl());
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
