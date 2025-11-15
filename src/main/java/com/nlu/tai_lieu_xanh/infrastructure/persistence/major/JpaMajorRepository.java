package com.nlu.tai_lieu_xanh.infrastructure.persistence.major;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nlu.tai_lieu_xanh.domain.major.Major;
import com.nlu.tai_lieu_xanh.domain.major.MajorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JpaMajorRepository implements MajorRepository {
  private SpringDataMajorRepository springDataMajorRepository;

  @Override
  public List<Major> searchMajorByName(String name) {
    return springDataMajorRepository.searchMajorByName(name);
  }

  @Override
  public List<Major> findAll() {
    return springDataMajorRepository.findAll();
  }

  @Override
  public Optional<Major> findById(Integer id) {
    return springDataMajorRepository.findById(id);
  }

  @Override
  public Major save(Major major) {
    return springDataMajorRepository.save(major);
  }

  @Override
  public void delete(Major major) {
    springDataMajorRepository.delete(major);
  }

  @Override
  public List<MajorWithPostCountProjection> findAllMajorsWithPostCount() {
    return springDataMajorRepository.findAllMajorsWithPostCount();
  }

}
