package com.coyote.big_city_library.rest_server_service.dto.reservation.my;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MyReservationDto {

    private MyBookDto book;

    private MyUserDto user;

    private ZonedDateTime createdAt;

    private ZonedDateTime notifiedAt;

    private MyExemplaryDto exemplary;

}
