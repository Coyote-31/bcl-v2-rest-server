package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.coyote.big_city_library.rest_server_model.dao.entities.Author;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server_model.dao.entities.Library;
import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import com.coyote.big_city_library.rest_server_model.dao.entities.Publisher;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;
import com.coyote.big_city_library.rest_server_service.dto.AuthorDto;
import com.coyote.big_city_library.rest_server_service.dto.ExemplaryDto;
import com.coyote.big_city_library.rest_server_service.dto.LibraryDto;
import com.coyote.big_city_library.rest_server_service.dto.LoanDto;
import com.coyote.big_city_library.rest_server_service.dto.PublisherDto;
import com.coyote.big_city_library.rest_server_service.dto.UserDto;

@Mapper(componentModel = "spring")
public interface SearchBookMapper {

    @Mapping(target = "available", ignore = true)
    @Mapping(target = "exemplariesByLibrary", ignore = true)
    SearchBookDto toDto(Book book);

    List<SearchBookDto> toDto(List<Book> books);

    Book toModel(SearchBookDto bookDto);

    // Author

    @Mapping(target = "books", ignore = true)
    AuthorDto toDto(Author author);

    @Mapping(target = "books", ignore = true)
    Author toModel(AuthorDto authorDto);

    // Exemplary

    @Mapping(target = "book", ignore = true)
    SearchExemplaryDto toDto(Exemplary exemplary);

    @Mapping(target = "book", ignore = true)
    Exemplary toModel(SearchExemplaryDto searchExemplaryDto);

    @Mapping(target = "book", ignore = true)
    ExemplaryDto toExemplaryDto(Exemplary exemplary);

    @Mapping(target = "book", ignore = true)
    Exemplary toExemplaryModel(ExemplaryDto exemplaryDto);

    // Library

    @Mapping(target = "exemplaries", ignore = true)
    SearchLibraryDto toDto(Library library);

    @Mapping(target = "exemplaries", ignore = true)
    Library toModel(SearchLibraryDto searchLibraryDto);

    @Mapping(target = "exemplaries", ignore = true)
    LibraryDto toLibraryDto(Library library);

    @Mapping(target = "exemplaries", ignore = true)
    Library toLibraryModel(LibraryDto libraryDto);

    // Loan

    @Mapping(target = "exemplary", ignore = true)
    SearchLoanDto toDto(Loan loan);

    @Mapping(target = "exemplary", ignore = true)
    Loan toModel(SearchLoanDto searchLoanDto);

    @Mapping(target = "exemplary", ignore = true)
    LoanDto toLoanDto(Loan loan);

    @Mapping(target = "exemplary", ignore = true)
    Loan toLoanModel(LoanDto loanDto);

    // User

    @Mapping(target = "loans", ignore = true)
    UserDto toUserDto(User user);

    @Mapping(target = "loans", ignore = true)
    User toUserModel(UserDto userDto);

    // Publisher

    @Mapping(target = "books", ignore = true)
    PublisherDto toDto(Publisher publisher);

    @Mapping(target = "books", ignore = true)
    Publisher toModel(PublisherDto publisherDto);

}
