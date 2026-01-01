package com.nlu.tai_lieu_xanh.application.post.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.application.major.service.MajorService;
import com.nlu.tai_lieu_xanh.application.mdoc.service.MDocService;
import com.nlu.tai_lieu_xanh.application.notification.service.NotificationService;
import com.nlu.tai_lieu_xanh.application.post.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostDetailResponse;
import com.nlu.tai_lieu_xanh.application.post.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.application.post.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.application.post.service.PostService;
import com.nlu.tai_lieu_xanh.application.tag.mapper.TagMapper;
import com.nlu.tai_lieu_xanh.application.tag.service.TagService;
import com.nlu.tai_lieu_xanh.application.user.service.AuthService;
import com.nlu.tai_lieu_xanh.domain.post.PostRepository;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.infrastructure.messaging.event.mdoc.PreviewEvent;
import com.nlu.tai_lieu_xanh.producer.PreviewMessageProducer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final MajorService majorService;
  private final NotificationService notificationService;
  private final TagService tagService;
  private final TagMapper tagMapper;
  private final MDocService mDocService;
  private final PostMapper postMapper;
  private final PreviewMessageProducer previewMessageProducer;

  @Transactional
  public PostResponse create(PostCreateRequest postRequest, MultipartFile file) {
    var post = postMapper.toPost(postRequest);
    Long currentUserId = authService.getCurrentUserId();
    var author = userRepository.findById(currentUserId).orElseThrow(UserNotFoundException::new);
    var major = majorService.findById(postRequest.majorId());
    var tags = tagService.getOrSaveTags(postRequest.tags());
    post.setAuthor(author);
    post.setMajor(major);
    post.setTags(tags);
    var mdoc = mDocService.uploadDocument(file, currentUserId);
    post.setMDoc(mdoc);
    var savedPost = postRepository.save(post);
    var previewMessage = new PreviewEvent(mdoc.getId(), Math.min(mdoc.getPages(), 5), mdoc.getObjectName());
    previewMessageProducer.sendCreatePreviewTask(previewMessage);
    // TODO notificationService.createNotification(postRequest.authorId(), "Tài liệu
    // của bạn đang được chờ duyệt");
    var tagReponseList = tagMapper.toTagResponseList(new ArrayList<>(post.getTags()));

    return postMapper.toPostResponse(savedPost, tagReponseList);
  }

  @Transactional
  public void viewPost(Long id) {
    postRepository.viewPost(id);
  }

  @Override
  public List<PostResponse> findPublishedPosts(Pageable pageable) {
    var posts = postRepository.getAll(pageable);
    return posts.stream().map(p -> {
      var postTagReponseList = tagMapper.toTagResponseList(new ArrayList<>(p.getTags()));
      return postMapper.toPostResponse(p, postTagReponseList);
    }).toList();
  }

  @Override
  public PostDetailResponse findPostDetail(Long postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findPostDetail'");
  }

}
