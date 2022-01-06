package com.coyote.big_city_library.rest_server.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter 
@Setter 
public class BookDto {

    private Integer id;

    private String title;

    private LocalDate publicationDate;

}
