package com.nlu.tai_lieu_xanh.application.major.service;

import com.nlu.tai_lieu_xanh.application.major.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.application.major.dto.request.MajorUpdateRequest;
import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;

public interface AdminMajorService {
  MajorResponse update(Integer MajorId, MajorUpdateRequest request);

  MajorResponse save(MajorCreateRequest request);

  void delete(Integer majorId);
}
