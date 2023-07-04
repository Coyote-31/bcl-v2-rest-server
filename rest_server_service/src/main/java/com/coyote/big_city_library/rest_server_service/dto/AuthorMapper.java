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
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    List<AuthorDto> toDto(List<Author> authors);

    Author toModel(AuthorDto authorDto);

    // Books

    @Mapping(target = "authors", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "authors", ignore = true)
    Book toModel(BookDto bookDto);

    // Exemplary

    @Mapping(target = "book", ignore = true)
    ExemplaryDto toDto(Exemplary exemplary);

    @Mapping(target = "book", ignore = true)
    Exemplary toModel(ExemplaryDto exemplaryDto);

    // Library

    @Mapping(target = "exemplaries", ignore = true)
    LibraryDto toDto(Library library);

    @Mapping(target = "exemplaries", ignore = true)
    Library toModel(LibraryDto libraryDto);

    // Loan

    @Mapping(target = "exemplary", ignore = true)
    LoanDto toDto(Loan loan);

    @Mapping(target = "exemplary", ignore = true)
    Loan toModel(LoanDto loanDto);

    // User

    @Mapping(target = "loans", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "loans", ignore = true)
    User toModel(UserDto userDto);

    // Publisher

    @Mapping(target = "books", ignore = true)
    PublisherDto toDto(Publisher publisher);

    @Mapping(target = "books", ignore = true)
    Publisher toModel(PublisherDto publisherDto);
}
