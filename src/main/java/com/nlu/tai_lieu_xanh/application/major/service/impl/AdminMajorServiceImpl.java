package com.nlu.tai_lieu_xanh.application.major.service.impl;

import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.application.major.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.application.major.dto.request.MajorUpdateRequest;
import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.application.major.mapper.MajorMapper;
import com.nlu.tai_lieu_xanh.application.major.service.AdminMajorService;
import com.nlu.tai_lieu_xanh.domain.major.MajorRepository;
import com.nlu.tai_lieu_xanh.exception.MajorNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMajorServiceImpl implements AdminMajorService {
  private final MajorRepository majorRepository;
  private final MajorMapper majorMapper;

  @Override
  public MajorResponse update(Long majorId, MajorUpdateRequest request) {
    var major = majorRepository
        .findById(majorId)
        .orElseThrow(MajorNotFoundException::new);
    major.updateName(request.name());
    return majorMapper.toMajorResponse(majorRepository.save(major));
  }

  @Override
  public MajorResponse save(MajorCreateRequest request) {
    var major = majorMapper.toMajor(request);
    var savedMajor = majorRepository.save(major);
    return majorMapper.toMajorResponse(savedMajor);
  }

  @Override
  public void delete(Long majorId) {
    var currentMajor = majorRepository
        .findById(majorId)
        .orElseThrow(MajorNotFoundException::new);
    majorRepository.delete(currentMajor);
  }
}
