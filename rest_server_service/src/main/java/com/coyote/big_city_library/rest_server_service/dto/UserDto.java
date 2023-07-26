package com.coyote.big_city_library.rest_server_service.dto;

import java.util.Set;
import com.coyote.big_city_library.rest_server_model.dao.attributes.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @NonNull
    private Role role = Role.USER;

    private Set<LoanDto> loans;

    private Set<ReservationDto> reservations;

}
