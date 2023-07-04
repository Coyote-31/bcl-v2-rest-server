package com.coyote.big_city_library.rest_server_service.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class LoanDto {

    private Integer id;

    @NonNull
    private LocalDate loanDate;

    @NonNull
    private Boolean extend = false;

    private LocalDate returnDate;

    @NonNull
    private ExemplaryDto exemplary;

    @NonNull
    private UserDto user;

}
