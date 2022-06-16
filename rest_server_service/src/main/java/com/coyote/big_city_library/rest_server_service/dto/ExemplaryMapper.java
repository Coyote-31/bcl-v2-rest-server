package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { LibraryMapper.class, BookMapper.class })
public interface ExemplaryMapper {

    ExemplaryDto toDto(Exemplary exemplary);

    List<ExemplaryDto> toDto(List<Exemplary> exemplaries);

    Exemplary toModel(ExemplaryDto exemplaryDto);
}
