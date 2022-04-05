package com.coyote.big_city_library.rest_server.dto;

import java.time.LocalDate;
import java.util.Set;

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
    private String title;

    @NonNull
    private LocalDate publicationDate;

    @NonNull
    private PublisherDto publisher;

    @NonNull
    private Set<AuthorDto> authors;

}
