package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;
import java.util.Set;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ReservationMapper {

    ReservationDto toDto(Reservation reservation);

    List<ReservationDto> toDto(List<Reservation> reservations);

    Set<ReservationDto> toDto(Set<Reservation> reservations);

    Reservation toModel(ReservationDto publisherDto);

    // Book

    @Mapping(target = "reservations", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "reservations", ignore = true)
    Book toModel(BookDto bookDto);

    // User

    @Mapping(target = "reservations", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "reservations", ignore = true)
    User toModel(UserDto userDto);
}
