package com.nlu.tai_lieu_xanh.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.major.service.MajorService;
import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.PresignedUrlRes;
import com.nlu.tai_lieu_xanh.application.mdoc.service.MDocService;
import com.nlu.tai_lieu_xanh.application.notification.service.NotificationService;
import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.application.post.service.PostService;
import com.nlu.tai_lieu_xanh.application.tag.service.TagService;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.post.PostRepository;
import com.nlu.tai_lieu_xanh.domain.post.PostSpecification;
import com.nlu.tai_lieu_xanh.domain.post.PostStatus;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewEvent;
import com.nlu.tai_lieu_xanh.infrastructure.storage.MinioStorageService;
import com.nlu.tai_lieu_xanh.producer.PreviewMessageProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final MajorService majorService;
  private final NotificationService notificationService;
  private final TagService tagService;
  private final MDocService mDocService;
  private final PostMapper postMapper = PostMapper.INSTANCE;
  private final MinioStorageService minioStorageService;
  private final PreviewMessageProducer previewMessageProducer;

  public List<PostResponse> findPublishedPosts(Pageable pageable) {
    var spec = PostSpecification.isPublished();
    return postRepository.findAll(spec, pageable)
        .stream()
        .map(postMapper::toPostResponse)
        .collect(Collectors.toList());
  }

  public PostResponse create(PostCreateRequest postRequest, MultipartFile file) {
    var post = postMapper.toPost(postRequest);
    var author = userRepository.findById(postRequest.authorId()).orElseThrow(UserNotFoundException::new);
    var major = majorService.findById(postRequest.majorId());
    var tags = tagService.getOrSaveTags(postRequest.tags());
    post.setPostStatus(PostStatus.PUBLISHED);
    post.setThumb("abc");
    post.setAuthor(author);
    post.setMajor(major);
    var mdoc = mDocService.uploadTemp(author.getId(), file);
    post.setDoc(mdoc);
    post.setTags(tags);
    var savedPost = postRepository.save(post);
    var previewMessage = new PreviewEvent(mdoc.getId(), Math.min(mdoc.getPages(), 5), mdoc.getUrl());
    previewMessageProducer.sendCreatePreviewTask(previewMessage);
    notificationService.createNotification(postRequest.authorId(), "Tài liệu của bạn đang được chờ duyệt");
    return postMapper.toPostDetailRes(savedPost);
  }

  public List<PostResponse> findNewPosts() {
    var pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "createdDate"));
    var spec = PostSpecification.isPublished();
    return postRepository.findAll(spec, pageable)
        .map(postMapper::toPostResponse).toList();
  }

  public List<PostResponse> findHotPosts() {
    var pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "views"));
    var spec = PostSpecification.isPublished();
    return postRepository.findAll(spec, pageable)
        .map(postMapper::toPostResponse).toList();
  }

  public List<PostResponse> findRelatedPosts(Long postId) {
    var pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "createdDate"));
    var post = findById(postId);
    var majorId = post.getMajor().getId();
    Specification<Post> spec = Specification.where(PostSpecification.hasMajorId(majorId))
        .and(PostSpecification.isNotId(postId))
        .and(PostSpecification.isPublished());

    return postRepository.findAll(spec, pageable)
        .stream()
        .map(postMapper::toPostResponse)
        .collect(Collectors.toList());
  }

  public void viewPost(Long id) {
    var post = findById(id);
    post.setViews(post.getViews() + 1);
    postRepository.save(post);
  }

  public List<PostResponse> searchPosts(String fileType, Long majorId, List<String> tags,
      String keyword, String sortBy, String direction, int page, int size) {
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Specification<Post> spec = Specification.where(null);
    spec = spec.and(PostSpecification.isPublished());
    System.out.println(keyword);
    Pageable pageable = PageRequest.of(page, size, sort);
    if (fileType != null) {
      spec = spec.and(PostSpecification.hasFileType(fileType));
    }
    if (majorId != null) {
      spec = spec.and(PostSpecification.hasMajorId(majorId));
    }
    if (tags != null && !tags.isEmpty()) {
      spec = spec.and(PostSpecification.hasTags(tags));
    }
    if (keyword != null && !keyword.isEmpty()) {
      spec = spec.and(PostSpecification.hasKeyword(keyword));
    }
    return postRepository.findAll(spec, pageable).stream()
        .map(postMapper::toPostResponse).toList();

  }

  @Override
  public PostDetailResponse getPostDetail(Long postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPostDetail'");
  }

  @Override
  public List<PostResponse> getNewPosts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getNewPosts'");
  }

  @Override
  public List<PostResponse> getHotPosts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getHotPosts'");
  }

  @Override
  public List<PostResponse> getRelatedPosts(Long postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRelatedPosts'");
  }

  @Override
  public List<PostResponse> getPostsByIds(String Ids) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPostsByIds'");
  }
}
