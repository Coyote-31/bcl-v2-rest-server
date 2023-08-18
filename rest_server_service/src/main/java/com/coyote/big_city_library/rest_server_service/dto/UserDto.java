package com.coyote.big_city_library.rest_server_service.dto;

import java.util.Set;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String pseudo;

    @NonNull
    @NotNull
    private String email;

    /**
     * Never sends to user
     *
     * @see com.coyote.big_city_library.rest_server.dto.UserMapper
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @NonNull
    @NotNull
    private Role role = Role.USER;

    private Set<LoanDto> loans;

    private Set<ReservationDto> reservations;

}
