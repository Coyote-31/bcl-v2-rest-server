package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.util.Set;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SearchExemplaryDto {

    private Integer id;

    private SearchLibraryDto library;

    private Set<SearchLoanDto> loans;

    private ReservationDto reservation;

}
