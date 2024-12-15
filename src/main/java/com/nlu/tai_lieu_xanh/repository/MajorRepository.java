package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Integer> {
    List<Major> searchMajorByName(String name);
}
