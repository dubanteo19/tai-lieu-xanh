package com.nlu.tai_lieu_xanh.infrastructure.persistence.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nlu.tai_lieu_xanh.domain.user.User;

interface SpringDataUserRepository extends JpaRepository<User, Integer> {
  boolean existsUserByEmail(String emai);

  Optional<User> findByEmail(String email);
}
