package com.coyote.big_city_library.rest_server.dto;

import com.coyote.big_city_library.rest_server.dao.entities.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toModel(UserDto userDto);
}