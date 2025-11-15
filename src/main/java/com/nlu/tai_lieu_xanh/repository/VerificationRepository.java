package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<VerificationToken, Integer> {
  Optional<VerificationToken> findByToken(String token);
}
