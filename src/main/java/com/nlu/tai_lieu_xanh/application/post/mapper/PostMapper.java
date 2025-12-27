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

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostMapper {
  private final SharedMapper sharedMapper;
  private final MajorMapper majorMapper;
  private final MDocMapper mDocMapper;

  public Post toPost(PostCreateRequest request) {
    return null;
  };

  public PostDetailResponse toPostDetailResponse(Post post) {
    var author = sharedMapper.toAuthor(post.getAuthor());
    var major = majorMapper.toMajorResponse(post.getMajor());
    var mdoc = mDocMapper.toMDocResponse(post.getDoc());
    String formatedCreatedDate = sharedMapper.formatDate(post.getCreatedDate());
    List<TagResponse> tags = List.of();
    return new PostDetailResponse(
        post.getId(),
        post.getTitle(),
        post.getDescription(),
        mdoc,
        author,
        major,
        tags,
        formatedCreatedDate);

  };

  public PostResponse toPostResponse(Post post, MetaData meta, List<TagResponse> tags) {
    var major = majorMapper.toMajorResponse(post.getMajor());
    var author = sharedMapper.toAuthor(post.getAuthor());
    return new PostResponse(
        post.getId(),
        post.getTitle(),
        post.getThumb(),
        post.getPostStatus().toString(),
        major,
        author,
        tags,
        meta);
  };

}
