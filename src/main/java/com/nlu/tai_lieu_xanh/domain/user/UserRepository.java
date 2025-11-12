package com.nlu.tai_lieu_xanh.domain.user;

import java.util.Optional;

public interface UserRepository {
  User save(User user);

  Optional<User> findById(Integer id);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

}
