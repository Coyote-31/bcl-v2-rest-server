package com.coyote.big_city_library.rest_server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class LibraryDto {

    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    private String phone;

}
