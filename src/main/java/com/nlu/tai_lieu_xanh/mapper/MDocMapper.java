package com.nlu.tai_lieu_xanh.mapper;

import com.nlu.tai_lieu_xanh.dto.request.m.doc.MDocReq;
import com.nlu.tai_lieu_xanh.model.MDoc;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MDocMapper {
    MDocMapper INSTANCE = Mappers.getMapper(MDocMapper.class);

    MDoc toMDoc(MDocReq mDocReq);
}
