package com.nlu.tai_lieu_xanh.application.post.mapper;

import java.awt.geom.QuadCurve2D;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.major.mapper.MajorMapper;
import com.nlu.tai_lieu_xanh.application.mdoc.mapper.MDocMapper;
import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.shared.SharedMapper;
import com.nlu.tai_lieu_xanh.application.tag.dto.response.TagResponse;
import com.nlu.tai_lieu_xanh.application.tag.mapper.TagMapper;
import com.nlu.tai_lieu_xanh.domain.major.Major;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.user.dto.response.post.PostDetailRes;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostMapper {
  private final SharedMapper sharedMapper;
  private final MajorMapper majorMapper;
  private final MDocMapper mDocMapper;
  private final TagMapper tagMapper;

  public Post toPost(PostCreateRequest request) {

  };

public  PostDetailResponse toPostDetailResponse(Post post) {
    var author = sharedMapper.toAuthor(post.getAuthor());
    var major = majorMapper.toMajorReponse(post.getMajor());
    var mdoc = mDocMapper.toMDocResponse(post.getDoc());
    String formatedCreatedDate = sharedMapper.formatDate(post.getCreatedDate());
    List<String> tags = List.of();
  return new PostDetailResponse(
      post.getId(),
      post.getTitle(),
      post.getDescription(),
      mdoc,
      author,
      formatedCreatedDate,
      major,
      tags)
  };

  public PostResponse toPostResponse(Post post) {

  };

}
