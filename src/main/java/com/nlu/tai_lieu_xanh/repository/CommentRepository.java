package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
