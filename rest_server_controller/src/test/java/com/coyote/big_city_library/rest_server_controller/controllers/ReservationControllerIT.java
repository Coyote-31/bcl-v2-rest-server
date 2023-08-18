package com.coyote.big_city_library.rest_server_controller.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = {"ADMIN"})
@Transactional
public class ReservationControllerIT {

    @Autowired
    ReservationController reservationController;

    @Autowired
    private MockMvc mvc;

    @Test
    void findAllReservations_returnReservationList() throws Exception {

        List<ReservationDto> reservations = reservationController.findAllReservations();
        log.debug("List<Reservation> size:{}", reservations.size());
        assertThat(reservations).hasSizeGreaterThanOrEqualTo(1);

        mvc.perform(MockMvcRequestBuilders.get("/reservations"))
           .andDo(print())
           .andExpect(status().isOk());
    }

}
