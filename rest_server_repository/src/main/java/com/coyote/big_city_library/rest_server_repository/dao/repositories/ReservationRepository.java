package com.coyote.big_city_library.rest_server_repository.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import com.coyote.big_city_library.rest_server_model.dao.entities.ReservationId;

/**
 * Repository class handling {@link Reservation} entity.
 *
 * @see ReservationId
 */
public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {

}
