package com.nlu.tai_lieu_xanh.application.post.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.major.mapper.MajorMapper;
import com.nlu.tai_lieu_xanh.application.mdoc.mapper.MDocMapper;
import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.MetaData;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.shared.SharedMapper;
import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.utils.UrlPreviewer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostMapper {
  private final SharedMapper sharedMapper;
  private final MajorMapper majorMapper;
  private final MDocMapper mDocMapper;

  public Post toPost(PostCreateRequest request) {
    return Post.create(request.title(), request.description());
  };

  public PostDetailResponse toPostDetailResponse(Post post, List<TagResponse> tags) {
    var major = majorMapper.toMajorResponse(post.getMajor());
    var author = sharedMapper.toAuthor(post.getAuthor());
    var mdoc = mDocMapper.toMDocResponse(post.getMdoc());
    String formatedCreatedDate = sharedMapper.formatDate(post.getCreatedDate());
    var meta = new MetaData(
        post.getViewCount(),
        0,
        post.getLikeCount(),
        formatedCreatedDate);
    return new PostDetailResponse(
        post.getId(),
        post.getTitle(),
        UrlPreviewer.generateThumbnail(mdoc.id()),
        post.getPostStatus(),
        major,
        author,
        tags,
        meta,
        post.getDescription(),
        mdoc);
  };

  public PostResponse toPostResponse(Post post, List<TagResponse> tags) {
    var major = majorMapper.toMajorResponse(post.getMajor());
    var author = sharedMapper.toAuthor(post.getAuthor());
    String formatedCreatedDate = sharedMapper.formatDate(post.getCreatedDate());
    var meta = new MetaData(
        post.getViewCount(),
        0,
        post.getLikeCount(),
        formatedCreatedDate);
    return new PostResponse(
        post.getId(),
        post.getTitle(),
        UrlPreviewer.generateThumbnail(post.getMdoc().getId()),
        post.getPostStatus(),
        major,
        author,
        tags,
        meta);
  };

}
