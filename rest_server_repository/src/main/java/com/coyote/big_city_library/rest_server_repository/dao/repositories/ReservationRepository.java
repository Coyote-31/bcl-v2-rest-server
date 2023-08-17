package com.coyote.big_city_library.rest_server_repository.dao.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import com.coyote.big_city_library.rest_server_model.dao.entities.ReservationId;


/**
 * Repository class handling {@link Reservation} entity.
 *
 * @see ReservationId
 */
public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {

    List<Reservation> findByNotifiedAtNotNull();

    List<Reservation> findByBookAndNotifiedAtIsNullOrderByCreatedAt(Book book);

}
