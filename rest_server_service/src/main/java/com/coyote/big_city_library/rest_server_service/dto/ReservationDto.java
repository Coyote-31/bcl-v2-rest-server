package com.coyote.big_city_library.rest_server_service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ReservationDto {

    @NonNull
    @NotNull
    private BookDto book;

    @NonNull
    @NotNull
    private UserDto user;

    @NonNull
    @NotNull
    private ZonedDateTime createdAt;

    private ZonedDateTime notifiedAt;

    private ExemplaryDto exemplary;

}
