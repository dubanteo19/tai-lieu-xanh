package com.nlu.tai_lieu_xanh.infrastructure.persistence.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.post.PostRepository;

import lombok.RequiredArgsConstructor;

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
  public List<Post> getAll(Pageable pageable) {
    return springDataPostRepository.findAll(pageable).toList();
  }

  @Override
  public void rejectPost(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'rejectPost'");
  }

  @Override
  public Optional<Post> findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Post save(Post post) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void viewPost(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'viewPost'");
  }

}
