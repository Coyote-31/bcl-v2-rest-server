package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server_model.dao.entities.Library;
import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ExemplaryMapper {

    ExemplaryDto toDto(Exemplary exemplary);

    List<ExemplaryDto> toDto(List<Exemplary> exemplaries);

    Exemplary toModel(ExemplaryDto exemplaryDto);

    // Library

    @Mapping(target = "exemplaries", ignore = true)
    LibraryDto toDto(Library library);

    @Mapping(target = "exemplaries", ignore = true)
    Library toModel(LibraryDto libraryDto);

    // Book

    @Mapping(target = "exemplaries", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "exemplaries", ignore = true)
    Book toModel(BookDto bookDto);

    // Loans

    @Mapping(target = "exemplary", ignore = true)
    LoanDto toDto(Loan loan);

    @Mapping(target = "exemplary", ignore = true)
    Loan toModel(LoanDto loanDto);
}
