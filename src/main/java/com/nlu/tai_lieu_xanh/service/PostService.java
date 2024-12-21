package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.dto.response.PostResponse;
import com.nlu.tai_lieu_xanh.exception.PostNotFoundException;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.model.MDoc;
import com.nlu.tai_lieu_xanh.model.Post;
import com.nlu.tai_lieu_xanh.repository.MDocRepository;
import com.nlu.tai_lieu_xanh.repository.PostRepository;
import com.nlu.tai_lieu_xanh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    MajorService majorService;
    TagService tagService;
    MDocRepository mDocRepository;
    PostMapper postMapper = PostMapper.INSTANCE;

    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }

    public Post save(PostCreateRequest postRequest) {
        var post = postMapper.toPost(postRequest);
        var author = userRepository.findById(postRequest.authorId()).orElseThrow(UserNotFoundException::new);
        var major = majorService.findById(postRequest.majorId());
        var tags = tagService.getOrSaveTags(postRequest.tags());
        post.setAuthor(author);
        post.setMajor(major);
        post.setTags(tags);
        return postRepository.save(post);
    }

    public MDoc findDocumentById(Integer postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post id %d not found".formatted(postId)));
        return post.getDoc();
    }
}
