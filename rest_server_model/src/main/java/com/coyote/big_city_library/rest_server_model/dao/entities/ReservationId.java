package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class ReservationId implements Serializable {

    @NonNull
    private Book book;

    @NonNull
    private User user;
}
