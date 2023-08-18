package com.coyote.big_city_library.rest_server_service.dto.reservation.my;

import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface MyReservationMapper {

    MyReservationDto toDto(Reservation reservation);

    List<MyReservationDto> toDto(List<Reservation> reservations);

}
