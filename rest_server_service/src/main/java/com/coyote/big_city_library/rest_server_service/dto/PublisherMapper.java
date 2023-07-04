package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;

import com.coyote.big_city_library.rest_server_model.dao.entities.Publisher;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface PublisherMapper {

    PublisherDto toDto(Publisher publisher);

    List<PublisherDto> toDto(List<Publisher> publishers);

    Publisher toModel(PublisherDto publisherDto);
}
