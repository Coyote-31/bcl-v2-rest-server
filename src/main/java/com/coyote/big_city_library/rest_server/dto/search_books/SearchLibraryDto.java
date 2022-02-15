package com.coyote.big_city_library.rest_server.dto.search_books;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter 
@Setter 
public class SearchLibraryDto {

    private Integer id;

    private String name;

}
