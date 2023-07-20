package com.coyote.big_city_library.rest_server_service.dto;

import java.time.ZonedDateTime;
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
    private BookDto book;

    @NonNull
    private UserDto user;

    @NonNull
    private ZonedDateTime createdAt;

    private ZonedDateTime notifiedAt;

}
