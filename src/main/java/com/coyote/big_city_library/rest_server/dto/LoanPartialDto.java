package com.coyote.big_city_library.rest_server.dto;

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
public class LoanPartialDto {

    @NonNull
    private LocalDate loanDate;

    @NonNull
    private Boolean extend = false;

    private LocalDate returnDate = null;

    @NonNull
    private ExemplaryOnlyIdDto exemplary;

    @NonNull
    private UserOnlyIdDto user;

}
