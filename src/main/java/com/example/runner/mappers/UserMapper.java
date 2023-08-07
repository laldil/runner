package com.example.runner.mappers;

import com.example.runner.dtos.UserInfoDto;
import com.example.runner.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserInfoDto mapUserToUserInfoDto(User user);
}
