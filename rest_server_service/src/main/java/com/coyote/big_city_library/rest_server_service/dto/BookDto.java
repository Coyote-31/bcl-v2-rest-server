package com.coyote.big_city_library.rest_server_service.dto;

import java.time.LocalDate;
import java.util.Set;
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
public class BookDto {

    private Integer id;

    @NonNull
    @NotNull
    private String title;

    @NonNull
    @NotNull
    private LocalDate publicationDate;

    @NonNull
    @NotNull
    private PublisherDto publisher;

    @NonNull
    @NotNull
    private Set<AuthorDto> authors;

    private String imgURL;

    private Set<ExemplaryDto> exemplaries;

    private Set<ReservationDto> reservations;

}
