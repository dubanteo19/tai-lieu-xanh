package com.nlu.tai_lieu_xanh.application.major.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorWithPostCountResponse;
import com.nlu.tai_lieu_xanh.application.major.mapper.MajorMapper;
import com.nlu.tai_lieu_xanh.application.major.service.MajorService;
import com.nlu.tai_lieu_xanh.domain.major.MajorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MajorServiceImpl implements MajorService {
  private final MajorRepository majorRepository;
  private final MajorMapper majorMapper;

  @Override
  public List<MajorResponse> findAll() {
    var majors = majorRepository.findAll();
    return majorMapper.toMajorResponseList(majors);
  }

  @Override
  public List<MajorResponse> searchMajorsByName(String name) {
    var majors = majorRepository.searchMajorByName(name);
    return majorMapper.toMajorResponseList(majors);
  }

  @Override
  public List<MajorWithPostCountResponse> findAllMajorsWithPostCount() {
    var majorsWithPostCount = majorRepository.findAllMajorsWithPostCount();
    return majorMapper
        .toMajorWithPostCountResponseList(majorsWithPostCount);
  }

}
