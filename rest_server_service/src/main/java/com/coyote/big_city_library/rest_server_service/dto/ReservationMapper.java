package com.coyote.big_city_library.rest_server_service.dto;

import java.util.List;
import java.util.Set;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.coyote.big_city_library.rest_server_model.dao.entities.Author;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server_model.dao.entities.Publisher;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ReservationMapper {

    ReservationDto toDto(Reservation reservation);

    List<ReservationDto> toDto(List<Reservation> reservations);

    Set<ReservationDto> toDto(Set<Reservation> reservations);

    Reservation toModel(ReservationDto reservationDto);

    // Book

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "exemplaries", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "exemplaries", ignore = true)
    Book toModel(BookDto bookDto);

    // User

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "loans", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "loans", ignore = true)
    User toModel(UserDto userDto);

    // Author

    @Mapping(target = "books", ignore = true)
    AuthorDto toDto(Author author);

    @Mapping(target = "books", ignore = true)
    Author toModel(AuthorDto authorDto);

    // Publisher

    @Mapping(target = "books", ignore = true)
    PublisherDto toDto(Publisher publisher);

    @Mapping(target = "books", ignore = true)
    Publisher toModel(PublisherDto publisherDto);

    // Exemplary

    @Mapping(target = "reservation", ignore = true)
    ExemplaryDto toDto(Exemplary exemplary);

    @Mapping(target = "reservation", ignore = true)
    Exemplary toModel(ExemplaryDto exemplaryDto);

}
