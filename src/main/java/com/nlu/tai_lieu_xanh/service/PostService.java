package com.nlu.tai_lieu_xanh.service;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.nlu.tai_lieu_xanh.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.exception.UserNotFoundException;
import com.nlu.tai_lieu_xanh.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.model.Post;
import com.nlu.tai_lieu_xanh.repository.PostRepository;
import com.nlu.tai_lieu_xanh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    PostMapper postMapper = PostMapper.INSTANCE;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(PostCreateRequest postRequest) {
        var post = postMapper.toPost(postRequest);
        var author = userRepository.findById(postRequest.authorId()).orElseThrow(UserNotFoundException::new);
        post.setAuthor(author);
        return postRepository.save(post);
    }
}
