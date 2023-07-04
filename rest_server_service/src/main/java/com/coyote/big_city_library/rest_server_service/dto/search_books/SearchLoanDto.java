package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.time.LocalDate;

import com.coyote.big_city_library.rest_server_service.dto.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SearchLoanDto {

    private Integer id;

    private LocalDate returnDate;

    @NonNull
    private LocalDate loanDate;

    private Boolean extend = false;

    @NonNull
    private SearchExemplaryDto exemplary;

    @NonNull
    private UserDto user;

}
