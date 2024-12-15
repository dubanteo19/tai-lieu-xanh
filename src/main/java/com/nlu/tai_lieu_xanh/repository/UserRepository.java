package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
