package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.coyote.big_city_library.rest_server_model.dao.entities.Author;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server_model.dao.entities.Library;
import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import com.coyote.big_city_library.rest_server_model.dao.entities.Publisher;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface LoanMapper {

    LoanDto toDto(Loan loan);

    List<LoanDto> toDto(List<Loan> loans);

    Loan toModel(LoanDto loanDto);

    // Exemplary

    @Mapping(target = "loans", ignore = true)
    ExemplaryDto toDto(Exemplary exemplary);

    @Mapping(target = "loans", ignore = true)
    Exemplary toModel(ExemplaryDto exemplaryDto);

    // User

    @Mapping(target = "loans", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "loans", ignore = true)
    User toModel(UserDto userDto);

    // Author

    @Mapping(target = "books", ignore = true)
    AuthorDto toDto(Author author);

    @Mapping(target = "books", ignore = true)
    Author toModel(AuthorDto authorDto);

    // Book

    @Mapping(target = "exemplaries", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "exemplaries", ignore = true)
    Book toModel(BookDto bookDto);

    // Publisher

    @Mapping(target = "books", ignore = true)
    PublisherDto toDto(Publisher publisher);

    @Mapping(target = "books", ignore = true)
    Publisher toModel(PublisherDto publisherDto);

    // Library

    @Mapping(target = "exemplaries", ignore = true)
    LibraryDto toDto(Library publisher);

    @Mapping(target = "exemplaries", ignore = true)
    Library toModel(LibraryDto publisherDto);
}
