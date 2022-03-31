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
     * Never sends to user
     * @see com.coyote.big_city_library.rest_server.dto.UserMapper
     */
    private String password;

    private Role role = Role.USER;

}
