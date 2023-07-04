package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SearchLibraryDto {

    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    private String phone;

    private Set<SearchExemplaryDto> exemplaries;

}
