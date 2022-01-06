package com.coyote.big_city_library.rest_server.dto;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Author;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    List<AuthorDto> toDto (List<Author> authors);

    Author toModel(AuthorDto authorDto);
}