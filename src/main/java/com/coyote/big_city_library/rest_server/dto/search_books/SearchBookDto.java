package com.coyote.big_city_library.rest_server.dto.search_books;
import java.time.LocalDate;
import java.util.List;

import com.coyote.big_city_library.rest_server.dto.AuthorDto;
import com.coyote.big_city_library.rest_server.dto.PublisherDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter 
@Setter 
public class SearchBookDto {

    private Integer id;

    private String title;

    private LocalDate publicationDate;

    private PublisherDto publisher;

    private List<AuthorDto> authors;

    private List<SearchExemplaryDto> exemplaries;

}
