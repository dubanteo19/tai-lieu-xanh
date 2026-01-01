package com.nlu.tai_lieu_xanh.application.post.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.application.post.service.DiscoveryService;
import com.nlu.tai_lieu_xanh.domain.post.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscoveryServiceImpl implements DiscoveryService {
  private final PostMapper postMapper;
  private final PostRepository postRepository;

  @Override
  @Transactional(readOnly = true)
  public List<PostResponse> getNewPosts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getNewPosts'");
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostResponse> getHotPosts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getHotPosts'");
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostResponse> getRelatedPosts(Long postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRelatedPosts'");
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostResponse> searchPosts(String fileType, Long majorId, List<String> tags,
      String keyword, String sortBy, String direction, int page, int size) {
    throw new UnsupportedOperationException("Unimplemented method 'getRelatedPosts'");
    /*
     * Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
     * Specification<Post> spec = Specification.where(null);
     * spec = spec.and(PostSpecification.isPublished());
     * System.out.println(keyword);
     * Pageable pageable = PageRequest.of(page, size, sort);
     * if (fileType != null) {
     * spec = spec.and(PostSpecification.hasFileType(fileType));
     * }
     * if (majorId != null) {
     * spec = spec.and(PostSpecification.hasMajorId(majorId));
     * }
     * if (tags != null && !tags.isEmpty()) {
     * spec = spec.and(PostSpecification.hasTags(tags));
     * }
     * if (keyword != null && !keyword.isEmpty()) {
     * spec = spec.and(PostSpecification.hasKeyword(keyword));
     * }
     * return postRepository.findAll(spec, pageable).stream()
     * .map(postMapper::toPostResponse).toList();
     */

  }

  public List<PostResponse> findPublishedPosts(Pageable pageable) {
    throw new UnsupportedOperationException("Unimplemented method 'getPostsByIds'");
    /*
     * var spec = PostSpecification.isPublished();
     * 
     * return postRepository.findAll(spec, pageable)
     * .stream()
     * .map(postMapper::toPostResponse)
     * .collect(Collectors.toList());
     */
  }

  @Override
  public List<PostResponse> getPostsByIds(String Ids) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPostsByIds'");
  }

}
