package com.coyote.big_city_library.rest_server.dto;

import com.coyote.big_city_library.rest_server.security.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer id;

    @NonNull
    private String pseudo;

    @NonNull
    private String email;

    /**
     * Never sends to user
     *
     * @see com.coyote.big_city_library.rest_server.dto.UserMapper
     */
    private String password;

    @NonNull
    private Role role = Role.USER;

}
