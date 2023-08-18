package com.coyote.big_city_library.rest_server_service.dto;

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
public class PublisherDto {

    private Integer id;

    @NonNull
    @NotNull
    private String name;

    private Set<BookDto> books;

}
