package com.coyote.big_city_library.rest_server.dto.search_books;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Book;
import com.coyote.big_city_library.rest_server.dto.AuthorMapper;
import com.coyote.big_city_library.rest_server.dto.PublisherMapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { PublisherMapper.class, AuthorMapper.class, SearchExemplaryMapper.class })
public interface SearchBookMapper {

    SearchBookDto toDto(Book book);

    List<SearchBookDto> toDto(List<Book> books);

    Book toModel(SearchBookDto bookDto);
}
