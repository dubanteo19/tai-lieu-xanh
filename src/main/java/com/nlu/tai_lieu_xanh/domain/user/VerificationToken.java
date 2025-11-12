package com.nlu.tai_lieu_xanh.domain.user;

import com.nlu.tai_lieu_xanh.infrastructure.persistence.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class VerificationToken extends AbstractModel {
  @Column(nullable = false, unique = true)
  private String token;
  @OneToOne
  @JoinColumn(name = "user_id")
  User user;

  protected VerificationToken() {
  }

  protected VerificationToken(User user, String token) {
    this.user = user;
    this.token = token;
  }
}
