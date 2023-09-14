package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Library;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface LibraryMapper {

    @Mapping(target = "exemplaries", ignore = true)
    LibraryDto toDto(Library library);

    @Mapping(target = "exemplaries", ignore = true)
    List<LibraryDto> toDto(List<Library> libraries);

    @Mapping(target = "exemplaries", ignore = true)
    Library toModel(LibraryDto libraryDto);
}
