package com.coyote.big_city_library.rest_server.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter 
@Setter 
public class ExemplaryDto {

    private Integer id;

    private LibraryDto library;

    private BookDto book;
}
