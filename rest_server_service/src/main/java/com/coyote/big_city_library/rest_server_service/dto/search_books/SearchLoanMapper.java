package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.util.Set;

import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchLoanMapper {

    @Mapping(target = "exemplary.book.available", ignore = true)
    @Mapping(target = "exemplary.book.exemplariesByLibrary", ignore = true)
    SearchLoanDto toDto(Loan loan);

    Set<SearchLoanDto> toDto(Set<Loan> loans);

    Loan toModel(SearchLoanDto loanDto);
}
