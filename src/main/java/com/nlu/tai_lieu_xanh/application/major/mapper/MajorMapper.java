package com.nlu.tai_lieu_xanh.application.major.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.major.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorResponse;
import com.nlu.tai_lieu_xanh.application.major.dto.response.MajorWithPostCountResponse;
import com.nlu.tai_lieu_xanh.domain.major.Major;
import com.nlu.tai_lieu_xanh.infrastructure.persistence.major.MajorWithPostCountProjection;

@Component
public class MajorMapper {

  public Major toMajor(MajorCreateRequest request) {
    return Major.create(request.name());
  }

  public MajorResponse toMajorResponse(Major major) {
    if (major == null) {
      return null;
    }
    return new MajorResponse(major.getId(), major.getName());
  }

  public List<MajorResponse> toMajorResponseList(List<Major> majors) {
    return majors
        .stream()
        .map(this::toMajorResponse)
        .toList();
  }

  public MajorWithPostCountResponse toMajorWithPostCountResponse(MajorWithPostCountProjection projection) {
    return new MajorWithPostCountResponse(
        projection.getId(),
        projection.getMajorName(),
        projection.getPostCount());
  }

  public List<MajorWithPostCountResponse> toMajorWithPostCountResponseList(
      List<MajorWithPostCountProjection> projections) {
    return projections
        .stream()
        .map(this::toMajorWithPostCountResponse)
        .toList();
  }
}
