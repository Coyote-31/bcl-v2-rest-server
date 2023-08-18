package com.coyote.big_city_library.rest_server_service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    // /**
    //  * Utilities getters
    //  */

    // public Integer getReservationsSize() {
    //     return book.getReservations().size();
    // }

    // public Integer getUserReservationPosition() {

    //     // Convert reservations Set to List
    //     List<ReservationDto> reservations = new ArrayList<>(book.getReservations());

    //     // Sort by CreatedAt Ascending
    //     reservations.sort((a, b) -> a.getCreatedAt().compareTo(b.createdAt));

    //     int count = 1;
    //     for (ReservationDto reservation : reservations) {
    //         log.debug("Reservation #{} -> createdAt:{}", count, reservation.getCreatedAt());
    //         count++;
    //     }

    //     return count;
    // }
}
