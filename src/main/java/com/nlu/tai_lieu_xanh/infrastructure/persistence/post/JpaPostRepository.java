package com.nlu.tai_lieu_xanh.infrastructure.persistence.post;

import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.post.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaPostRepository implements PostRepository {
  private final SpringDataPostRepository springDataPostRepository;

  @Override
  public List<Post> getAllReviewPosts(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllReviewPosts'");
  }

  @Override
  public List<Post> getAllRejectedPosts(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllRejectedPosts'");
  }

  @Override
  public List<Post> getAllDeletedPosts(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllDeletedPosts'");
  }

  @Override
  public void rejectPost(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'rejectPost'");
  }

  @Override
  public Optional<Post> findById(Long id) {
    return springDataPostRepository.findById(id);
  }

  @Override
  public Post save(Post post) {
    return springDataPostRepository.save(post);
  }

  @Override
  public void viewPost(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'viewPost'");
  }

  @Override
  public List<Post> findNextPosts(LocalDateTime cursor, Pageable pageable) {
    return springDataPostRepository.findNextPosts(cursor, pageable);
  }
}
