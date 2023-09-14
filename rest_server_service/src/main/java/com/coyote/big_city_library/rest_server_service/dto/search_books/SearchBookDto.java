package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.time.LocalDate;
import java.util.Set;
import com.coyote.big_city_library.rest_server_service.dto.AuthorDto;
import com.coyote.big_city_library.rest_server_service.dto.PublisherDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SearchBookDto {

    private Integer id;

    private String title;

    private LocalDate publicationDate;

    private PublisherDto publisher;

    private Set<AuthorDto> authors;

    private Set<SearchExemplaryDto> exemplaries;

    private String imgURL;

    private Set<ReservationDto> reservations;

}
