package com.coyote.big_city_library.rest_server.dto.search_books;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Library;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchLibraryMapper {

    SearchLibraryDto toDto(Library library);

    List<SearchLibraryDto> toDto (List<Library> libraries);

    Library toModel(SearchLibraryDto libraryDto);
}