package com.coyote.big_city_library.rest_server_service.dto;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.coyote.big_city_library.rest_server_model.dao.entities.ReservationId;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ReservationIdMapper {

    @Mapping(source = "book", target = "bookId")
    @Mapping(source = "user", target = "userId")
    ReservationIdDto toDto(ReservationId reservationId);

    @Mapping(source = "bookId", target = "book")
    @Mapping(source = "userId", target = "user")
    ReservationId toModel(ReservationIdDto publisherDto);

}
