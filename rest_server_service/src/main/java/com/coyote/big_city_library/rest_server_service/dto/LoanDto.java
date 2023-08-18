package com.coyote.big_city_library.rest_server_service.dto;

import java.time.LocalDate;
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
public class LoanDto {

    private Integer id;

    @NonNull
    @NotNull
    private LocalDate loanDate;

    @NonNull
    @NotNull
    private Boolean extend = false;

    private LocalDate returnDate;

    @NonNull
    @NotNull
    private ExemplaryDto exemplary;

    @NonNull
    @NotNull
    private UserDto user;

}
