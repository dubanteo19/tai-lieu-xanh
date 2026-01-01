package com.nlu.tai_lieu_xanh.infrastructure.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nlu.tai_lieu_xanh.domain.post.Post;

public interface SpringDataPostRepository extends JpaRepository<Post, Long> {

}
