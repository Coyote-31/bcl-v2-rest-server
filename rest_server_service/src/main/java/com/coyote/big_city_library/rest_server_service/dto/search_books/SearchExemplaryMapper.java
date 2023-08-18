package com.coyote.big_city_library.rest_server_service.dto.search_books;

import java.util.List;
import org.mapstruct.Mapper;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;

@Mapper(componentModel = "spring", uses = {SearchLibraryMapper.class})
public interface SearchExemplaryMapper {

    SearchExemplaryDto toDto(Exemplary exemplary);

    List<SearchExemplaryDto> toDto(List<Exemplary> exemplaries);
}
