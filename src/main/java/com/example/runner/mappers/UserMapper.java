package com.example.runner.mappers;

import com.example.runner.dtos.LoginResponse;
import com.example.runner.dtos.RegistrationUserRequest;
import com.example.runner.dtos.RegistrationUserResponse;
import com.example.runner.dtos.UpdateUserDto;
import com.example.runner.dtos.UserInfoDto;
import com.example.runner.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserInfoDto userToUserInfoDto(UserEntity user);

    UserEntity registrationUserDtoToUser(RegistrationUserRequest request);

    LoginResponse userEntityToLoginResponse(UserEntity user);

    RegistrationUserResponse userEntityToRegistrationResponse(UserEntity user);

    void updateUserEntity(UpdateUserDto updateUserDto, @MappingTarget UserEntity userEntity);
}
