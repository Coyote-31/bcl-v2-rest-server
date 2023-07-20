package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "password", ignore = true)
    List<UserDto> toDto(List<User> users);

    User toModel(UserDto userDto);

    // Loan

    @Mapping(target = "user", ignore = true)
    LoanDto toDto(Loan loan);

    @Mapping(target = "user", ignore = true)
    Loan toModel(LoanDto loanDto);

}
