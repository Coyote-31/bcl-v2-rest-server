package com.coyote.big_city_library.rest_server_service.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class ReservationIdDto {

    @NonNull
    @NotNull
    private Integer bookId;

    @NonNull
    @NotNull
    private Integer userId;

}
