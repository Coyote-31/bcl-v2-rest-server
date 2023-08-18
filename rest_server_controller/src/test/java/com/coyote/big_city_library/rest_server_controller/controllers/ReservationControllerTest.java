package com.coyote.big_city_library.rest_server_controller.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.coyote.big_city_library.rest_server_service.dto.AuthorDto;
import com.coyote.big_city_library.rest_server_service.dto.BookDto;
import com.coyote.big_city_library.rest_server_service.dto.PublisherDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.UserDto;
import com.coyote.big_city_library.rest_server_service.services.ReservationService;

@WebMvcTest
@ContextConfiguration(classes = ReservationController.class)
@WithMockUser(authorities = {"ADMIN"})
public class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void findAllReservations() throws Exception {

        // BookDto

        Set<AuthorDto> authors = new HashSet<>();
        authors.add(new AuthorDto("AuthorName"));

        BookDto bookDto = new BookDto(
                "BookTitle",
                LocalDate.now(),
                new PublisherDto("PublisherName"),
                authors);

        // UserDto

        UserDto userDto = new UserDto("UserPseudo", "User@mail.com");

        ReservationDto reservationDto = new ReservationDto(bookDto, userDto, ZonedDateTime.now());

        List<ReservationDto> reservations = new ArrayList<>();

        reservations.add(reservationDto);

        when(reservationService.findAllReservations()).thenReturn(reservations);

        mvc.perform(MockMvcRequestBuilders.get("/reservations"))
           .andDo(print())
           .andExpect(status().isOk());
    }

}
