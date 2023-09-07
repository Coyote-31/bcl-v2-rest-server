package com.coyote.big_city_library.rest_server_controller.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = {"ADMIN"})
@Transactional
public class ReservationControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    void findAllReservations_returnReservationList() throws Exception {

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/reservations"))
                              .andDo(print())
                              .andExpect(status().isOk())
                              .andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        List<ReservationDto> reservationDtos =
                Arrays.asList(objectMapper.readValue(json, ReservationDto[].class));

        assertThat(reservationDtos).hasSizeGreaterThanOrEqualTo(1);

        log.debug("List<ReservationDto> size:{}", reservationDtos.size());
        log.debug("Reservation #1 : bookTitle='{}', userPseudo='{}'",
                reservationDtos.get(0).getBook().getTitle(),
                reservationDtos.get(0).getUser().getPseudo());
    }

}
