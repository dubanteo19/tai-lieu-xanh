package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
}