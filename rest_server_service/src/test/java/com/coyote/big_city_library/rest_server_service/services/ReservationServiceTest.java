package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    ReservationService reservationService;

    @BeforeEach
    void init() {
        reservationService = new ReservationService();
    }

    @Test
    void addReservation_returnReservationDto() {

        assertThat(true).isTrue();
    }

    // TODO : unit test

}
