package com.coyote.big_city_library.rest_server.dto;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Publisher;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    PublisherDto toDto(Publisher publisher);

    List<PublisherDto> toDto(List<Publisher> publishers);

    Publisher toModel(PublisherDto publisherDto);
}
