package com.nlu.tai_lieu_xanh.application.major.service;

import java.util.List;

import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorWithPostCountResponse;
import com.nlu.tai_lieu_xanh.domain.major.Major;

public interface MajorService {
  List<MajorResponse> findAll();

  List<MajorResponse> searchMajorsByName(String name);

  List<MajorWithPostCountResponse> findAllMajorsWithPostCount();

  Major findById(Long id);
}
