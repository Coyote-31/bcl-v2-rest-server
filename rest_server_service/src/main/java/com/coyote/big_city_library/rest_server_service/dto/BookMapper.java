package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.coyote.big_city_library.rest_server_model.dao.entities.Author;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface BookMapper {

    BookDto toDto(Book book);

    List<BookDto> toDto(List<Book> books);

    Book toModel(BookDto bookDto);

    // Author

    @Mapping(target = "books", ignore = true)
    AuthorDto toDto(Author author);

    @Mapping(target = "books", ignore = true)
    Author toModel(AuthorDto authorDto);

    // Exemplary

    @Mapping(target = "book", ignore = true)
    ExemplaryDto toDto(Exemplary exemplary);

    @Mapping(target = "book", ignore = true)
    Exemplary toModel(ExemplaryDto exemplaryDto);

}
