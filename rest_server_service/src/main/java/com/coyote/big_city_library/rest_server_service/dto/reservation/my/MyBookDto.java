package com.coyote.big_city_library.rest_server_service.dto.reservation.my;

import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MyBookDto {

    private Integer id;

    private String title;

    private LocalDate publicationDate;

    private MyPublisherDto publisher;

    private Set<MyAuthorDto> authors;

    private String imgURL;

    private Set<MyExemplaryDto> exemplaries;

    private Set<MyReservationInBookDto> reservations;

}
