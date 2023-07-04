package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Library;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface LibraryMapper {

    LibraryDto toDto(Library library);

    List<LibraryDto> toDto(List<Library> libraries);

    Library toModel(LibraryDto libraryDto);
}
