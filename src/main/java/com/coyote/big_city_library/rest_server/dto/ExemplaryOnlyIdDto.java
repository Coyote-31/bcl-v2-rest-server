package com.coyote.big_city_library.rest_server.dto;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Setter
public class ExemplaryOnlyIdDto {

    @NonNull
    private Integer id;

}
