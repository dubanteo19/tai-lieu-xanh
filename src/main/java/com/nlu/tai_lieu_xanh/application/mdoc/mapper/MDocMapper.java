package com.nlu.tai_lieu_xanh.application.mdoc.mapper;

import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.mdoc.dto.response.MDocResponse;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;

@Component
public class MDocMapper {
  public MDocResponse toMDocResponse(MDoc mDoc) {
    if (mDoc == null) {
      return null;
    }
    return new MDocResponse(
        mDoc.getId(),
        mDoc.getFileName(),
        mDoc.getFileType().toString(),
        mDoc.getPages(),
        mDoc.getDownloads(),
        mDoc.getFileSize(),
        mDoc.getUrl());
  }
}
