package com.coyote.big_city_library.rest_server.dto;

import com.coyote.big_city_library.rest_server.security.Role;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter 
@Setter 
public class UserDto {

    private Integer id;

    private String pseudo;

    private String email;

    /**
     * This password need SHA Encryption
     * before mapping to entitie User
     * @see com.coyote.big_city_library.rest_server.dao.entities.User
     */
    private String password;

    private Role role = Role.USER;

}
