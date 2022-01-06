package com.coyote.big_city_library.rest_server.dto;

import java.util.List;
import java.util.Optional;

import com.coyote.big_city_library.rest_server.dao.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "password", ignore = true)
    default Optional<UserDto> toDto(Optional<User> user) {
        return Optional.ofNullable(toDto(user.get()));
    }

    @Mapping(target = "password", ignore = true)
    List<UserDto> toDto (List<User> users);

    User toModel(UserDto userDto);
}