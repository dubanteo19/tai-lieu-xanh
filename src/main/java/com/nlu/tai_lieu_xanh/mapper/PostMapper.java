package com.nlu.tai_lieu_xanh.mapper;

import com.nlu.tai_lieu_xanh.dto.request.PostCreateRequest;
import com.nlu.tai_lieu_xanh.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toPost(PostCreateRequest request);


}
