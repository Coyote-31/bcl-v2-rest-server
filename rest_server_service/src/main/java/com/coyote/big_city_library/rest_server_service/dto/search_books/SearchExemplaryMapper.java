package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { SearchLibraryMapper.class })
public interface SearchExemplaryMapper {

    SearchExemplaryDto toDto(Exemplary exemplary);

    List<SearchExemplaryDto> toDto(List<Exemplary> exemplaries);

    Exemplary toModel(SearchExemplaryDto exemplaryDto);
}
