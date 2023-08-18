package com.coyote.big_city_library.rest_server_service.dto.reservation.my;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MyLibraryDto {

    private Integer id;

    private String name;

    private String address;

    private String phone;

}
