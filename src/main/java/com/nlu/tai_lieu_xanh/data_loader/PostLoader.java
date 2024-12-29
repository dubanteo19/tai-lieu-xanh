package com.nlu.tai_lieu_xanh.data_loader;

import com.nlu.tai_lieu_xanh.repository.MDocRepository;
import com.nlu.tai_lieu_xanh.repository.PostRepository;
import com.nlu.tai_lieu_xanh.service.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)

public class PostLoader implements CommandLineRunner {
    private final PostService postService;
    private final PostRepository postRepository;
    private final MDocRepository docRepository;
    @Value("${include-data-loader}")
    boolean includeDataLoader;

    public PostLoader(PostService postService, PostRepository postRepository, MDocRepository docRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.docRepository = docRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (includeDataLoader) {
            return;
        }
//        PostCreateRequest postCreate1 = new PostCreateRequest("Sinh A1-Di truyền học", "", 2, 4, List.of("Sinh", "Di truyền học"));
//        var savedPost1 = postService.save(postCreate1);
//        var post1Doc = MDoc.builder().fileName("sinh.pdf").fileSize(30L)
//                .fileType(FileType.PDF).url("documents/sinh.pdf").build();
//        var savedPost1Doc = docRepository.save(post1Doc);
//        savedPost1.setDoc(savedPost1Doc);
//        postRepository.save(savedPost1);
    }
}
