package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ExemplaryMapper.class, UserMapper.class })
public interface LoanMapper {

    LoanDto toDto(Loan loan);

    List<LoanDto> toDto(List<Loan> loans);

    Loan toModel(LoanDto loanDto);
}
