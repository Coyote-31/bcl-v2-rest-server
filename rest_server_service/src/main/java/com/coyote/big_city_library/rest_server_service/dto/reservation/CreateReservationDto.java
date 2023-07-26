package com.coyote.big_city_library.rest_server_service.dto.reservation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class CreateReservationDto {

    @NonNull
    private Integer bookId;

    @NonNull
    private Integer userId;

}
