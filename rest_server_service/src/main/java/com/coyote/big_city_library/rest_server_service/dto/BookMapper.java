package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Book;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { PublisherMapper.class, AuthorMapper.class })
public interface BookMapper {

    BookDto toDto(Book book);

    List<BookDto> toDto(List<Book> books);

    Book toModel(BookDto bookDto);
}
