package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SearchLibraryMapper.class})
public interface SearchExemplaryMapper {

    @Mapping(target = "book.available", ignore = true)
    @Mapping(target = "book.exemplariesByLibrary", ignore = true)
    SearchExemplaryDto toDto(Exemplary exemplary);

    List<SearchExemplaryDto> toDto(List<Exemplary> exemplaries);

    Exemplary toModel(SearchExemplaryDto exemplaryDto);
}
