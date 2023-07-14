package com.coyote.big_city_library.rest_server_repository.dao.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ReservationRepositoryIT {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void test() {

        List<Reservation> reservations = reservationRepository.findAll();
        log.warn("Logger : " + reservations.get(0).getBook().getTitle());
        log.debug(reservations.get(0).toString());
        log.debug("equals : {}", reservations.get(0).equals(reservations.get(0)));
        log.debug("hash : {}", reservations.get(0).hashCode());
        assertThat(reservations).hasSize(1);
    }

}
