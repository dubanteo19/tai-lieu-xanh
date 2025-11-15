package com.nlu.tai_lieu_xanh.application.major.mapper;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.domain.major.Major;

@Component
public class MajorMapper {
  public MajorResponse toMajorReponse(Major major) {
    if (major == null) {
      return null;
    }
    return new MajorResponse(major.getId(), major.getName());
  }

}
