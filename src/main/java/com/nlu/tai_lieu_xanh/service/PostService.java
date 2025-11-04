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

import com.nlu.tai_lieu_xanh.dto.request.m.doc.PreviewEvent;
import com.nlu.tai_lieu_xanh.dto.request.post.PostCreateRequest;
import com.nlu.tai_lieu_xanh.dto.response.mdoc.PresignedUrlRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostDetailRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.dto.response.post.PostSpecification;
import com.nlu.tai_lieu_xanh.exception.PostNotFoundException;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.model.MDoc;
import com.nlu.tai_lieu_xanh.model.Post;
import com.nlu.tai_lieu_xanh.model.PostStatus;
import com.nlu.tai_lieu_xanh.producer.PreviewMessageProducer;
import com.nlu.tai_lieu_xanh.repository.PostRepository;
import com.nlu.tai_lieu_xanh.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final MajorService majorService;
  private final NotificationService notificationService;
  private final TagService tagService;
  private final MDocService mDocService;
  private final PostMapper postMapper = PostMapper.INSTANCE;
  private final MinioStorageService minioStorageService;
  private final PreviewMessageProducer previewMessageProducer;

  public List<PostResponse> findAllDeletedPost(Pageable pageable) {
    var spec = PostSpecification.isDeleted();
    return postRepository.findAll(spec, pageable)
        .stream()
        .map(postMapper::toPostResponse)
        .collect(Collectors.toList());
  }

  public List<PostResponse> findAllPost(Pageable pageable) {
    var spec = PostSpecification.isNotDeleted();
    return postRepository.findAll(spec, pageable)
        .stream()
        .map(postMapper::toPostResponse)
        .collect(Collectors.toList());
  }

  public List<PostResponse> findPublishedPosts(Pageable pageable) {
    var spec = PostSpecification.isPublished();
    return postRepository.findAll(spec, pageable)
        .stream()
        .map(postMapper::toPostResponse)
        .collect(Collectors.toList());
  }

  public Post findById(Integer id) {
    return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
  }

  public PostDetailRes save(PostCreateRequest postRequest, MultipartFile file) {
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

  public MDoc findDocumentById(Integer postId) {
    var post = postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post id %d not found".formatted(postId)));
    return post.getDoc();
  }

  public PostDetailRes findPostDetailById(Integer id) {
    var post = findById(id);
    return postMapper.toPostDetailRes(post);
  }

  public List<PostResponse> findNewPosts() {
    var pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "createdDate"));
    var spec = PostSpecification.isPublished();
    return postRepository.findAll(spec, pageable)
        .map(postMapper::toPostResponse).toList();
  }

  public void delete(Integer id) {
    var post = findById(id);
    postRepository.delete(post);
  }

  public List<PostResponse> findHotPosts() {
    var pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "views"));
    var spec = PostSpecification.isPublished();
    return postRepository.findAll(spec, pageable)
        .map(postMapper::toPostResponse).toList();
  }

  public void viewPost(Integer id) {
    var post = findById(id);
    post.setViews(post.getViews() + 1);
    postRepository.save(post);
  }

  public List<PostResponse> findRelatedPosts(Integer postId) {
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

  public List<PostResponse> searchPosts(String fileType, Integer majorId, List<String> tags,
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

  public PresignedUrlRes download(Integer id) {
    var post = findById(id);
    var doc = post.getDoc();
    doc.increaseDownloadCount();
    post.setDoc(doc);
    postRepository.save(post);
    String url = minioStorageService.getFileUrl(doc.getFileName());
    return new PresignedUrlRes(url);
  };

  public List<PostResponse> getPostByIdList(String idList) {
    var postIdList = Arrays.stream(idList.split("-"))
        .map(Integer::parseInt)
        .toList();

    return postRepository.findAllById(postIdList)
        .stream()
        .filter(post -> post.getPostStatus() == PostStatus.PUBLISHED)
        .map(postMapper::toPostResponse).toList();
  }

  public void setPostStatus(Integer id, PostStatus postStatus) {
    var post = findById(id);
    post.setPostStatus(postStatus);
    postRepository.save(post);
  }

  public List<PostResponse> findAllReviewPost(Pageable pageable) {
    var spec = PostSpecification.isReview();
    return postRepository.findAll(spec, pageable)
        .stream()
        .map(postMapper::toPostResponse)
        .collect(Collectors.toList());
  }

  public void rejectPost(Integer id, PostStatus postStatus, String reason) {
    var post = findById(id);
    var userId = post.getAuthor().getId();
    post.setPostStatus(postStatus);
    notificationService.createNotification(userId, "Tài liệu của bạn đã bị từ chối vì lý do %s".formatted(reason));
  }
}
