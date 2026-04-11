package com.nlu.tai_lieu_xanh.application.post.service.impl;

import com.nlu.tai_lieu_xanh.application.mdoc.service.MDocService;
import com.nlu.tai_lieu_xanh.application.notification.service.NotificationService;
import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.application.post.service.PostService;
import com.nlu.tai_lieu_xanh.application.shared.response.CursorResponse;
import com.nlu.tai_lieu_xanh.application.tag.mapper.TagMapper;
import com.nlu.tai_lieu_xanh.application.tag.service.TagService;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import com.nlu.tai_lieu_xanh.domain.major.Major;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.post.PostRepository;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.exception.PostNotFoundException;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewEvent;
import com.nlu.tai_lieu_xanh.producer.PreviewMessageProducer;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final NotificationService notificationService;
  private final EntityManager entityManager;
  private final TagService tagService;
  private final TagMapper tagMapper;
  private final MDocService mDocService;
  private final PostMapper postMapper;
  private final PreviewMessageProducer previewMessageProducer;

  @Override
  public PostResponse create(PostCreateRequest request, MultipartFile file) {
    Long currentUserId = authService.getCurrentUserId();
    var mdoc = mDocService.uploadDocument(file, currentUserId);
    var savedPost = performSave(request, mdoc, currentUserId);
    var previewMessage =
        new PreviewEvent(mdoc.getId(), Math.min(mdoc.getPages(), 5), mdoc.getObjectName());
    previewMessageProducer.sendCreatePreviewTask(previewMessage);
    notificationService.sendNotification(currentUserId, "Tài liệu của bạn đang được chờ duyệt");
    var tagReponseList = tagMapper.toTagResponseList(savedPost.getTags());
    return postMapper.toPostResponse(savedPost, tagReponseList);
  }

  @Override
  @Transactional
  public Post performSave(PostCreateRequest request, MDoc mdoc, Long userId) {
    var post = postMapper.toPost(request);
    var author = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    var major = entityManager.getReference(Major.class, request.majorId());
    var tags = tagService.getOrSaveTags(request.tags());
    post.setAuthor(author);
    post.setMajor(major);
    post.setTags(tags);
    post.setMDoc(mdoc);
    return postRepository.save(post);
  }

  @Transactional
  public void viewPost(Long id) {
    postRepository.viewPost(id);
  }

  @Override
  public CursorResponse<PostResponse> findPublishedPosts(LocalDateTime cursor) {
    int SIZE = 10;
    var pageable = PageRequest.of(0, SIZE + 1);
    var posts = postRepository.findNextPosts(cursor, pageable);
    boolean hasNext = posts.size() > SIZE;
    var limitPosts = hasNext ? posts.subList(0, SIZE) : posts;
    LocalDateTime nextCursor = null;
    if (hasNext) {
      nextCursor = limitPosts.getLast().getCreatedDate();
    }
    var items =
        limitPosts.stream()
            .map(p -> postMapper.toPostResponse(p, tagMapper.toTagResponseList(p.getTags())))
            .toList();
    return new CursorResponse<>(items, nextCursor, hasNext);
  }

  @Override
  @Transactional
  public PostDetailResponse findPostDetail(Long postId) {
    var post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new PostNotFoundException("post not found"));
    var postTagReponseList = tagMapper.toTagResponseList(post.getTags());
    return postMapper.toPostDetailResponse(post, postTagReponseList);
  }
}
