package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.BookRepository;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.ReservationRepository;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.UserRepository;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationMapper;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;
import com.coyote.big_city_library.rest_server_service.security.JwtProvider;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    JwtProvider jwtProvider;

    @Mock
    BookRepository bookRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ReservationMapper reservationMapper;

    @BeforeEach
    void init() {}

    @Test
    void addReservation_returnReservationDto() throws UserAccessDeniedException {

        // --- ARRANGE ---
        // jwt
        when(jwtProvider.getUsername("Token")).thenReturn("UserTest");

        // book
        Book book = new Book();
        book.setId(1);
        // book exemplary
        Exemplary exemplary = new Exemplary();
        exemplary.setId(1);
        book.addExemplary(exemplary);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        // user
        User user = new User();
        user.setId(1);
        user.setPseudo("UserTest");
        when(userRepository.findByPseudo(any())).thenReturn(Optional.of(user));

        // reservation
        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        when(reservationRepository.existsById(any())).thenReturn(false);
        when(reservationRepository.save(any())).thenReturn(reservation);

        // mapper
        when(reservationMapper.toDto(reservation)).thenReturn(new ReservationDto());

        // --- ACT ---
        ReservationDto reservationDto = reservationService.addReservation(1, "Token");

        // --- ASSERT ---
        verify(jwtProvider, times(1)).getUsername("Token");
        verify(bookRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findByPseudo("UserTest");
        verify(reservationRepository, times(1)).existsById(any());
        verify(reservationRepository, times(1)).save(any());
        verify(reservationMapper, times(1)).toDto(any(Reservation.class));
        assertThat(reservationDto).isNotNull();
    }

}
