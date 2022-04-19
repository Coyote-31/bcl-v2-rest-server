package com.coyote.big_city_library.rest_server.dto.search_books;

import java.util.Set;

import com.coyote.big_city_library.rest_server.dao.entities.Loan;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchLoanMapper {

    SearchLoanDto toDto(Loan loan);

    Set<SearchLoanDto> toDto(Set<Loan> loans);

    Loan toModel(SearchLoanDto loanDto);
}
