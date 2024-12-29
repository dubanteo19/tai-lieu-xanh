package com.nlu.tai_lieu_xanh.mapper;

import com.nlu.tai_lieu_xanh.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.dto.response.user.UserInfoRes;
import com.nlu.tai_lieu_xanh.model.Post;
import com.nlu.tai_lieu_xanh.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserCreateRequest userCreateRequest);

    UserInfoRes toUserRes(User user);

    default Integer toFriends(List<User> friends) {
        return friends.size();
    }

    default Integer toPosts(List<Post> posts) {
        return posts.size();
    }
}
