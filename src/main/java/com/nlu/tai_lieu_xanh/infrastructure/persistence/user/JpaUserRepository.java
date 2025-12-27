package com.nlu.tai_lieu_xanh.infrastructure.persistence.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nlu.tai_lieu_xanh.domain.user.User;
import com.nlu.tai_lieu_xanh.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {
  private final SpringDataUserRepository jpaRepo;

  @Override
  public User save(User user) {
    return jpaRepo.save(user);
  }

  @Override
  public Optional<User> findById(Long id) {
    return jpaRepo.findById(id);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return jpaRepo.findByEmail(email);
  }

  @Override
  public boolean existsByEmail(String email) {
    return jpaRepo.existsUserByEmail(email);
  }

}
