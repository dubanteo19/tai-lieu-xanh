package com.nlu.tai_lieu_xanh.mapper;

import com.nlu.tai_lieu_xanh.dto.response.comment.CommentRes;
import com.nlu.tai_lieu_xanh.dto.response.post.Author;
import com.nlu.tai_lieu_xanh.model.Comment;
import com.nlu.tai_lieu_xanh.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = SharedConfig.class)
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "user", target = "author", qualifiedByName = "toAuthor")
    @Mapping(source = "createdDate", target = "createdDate", qualifiedByName = "formatDate")
    CommentRes toCommentRes(Comment comment);

}
