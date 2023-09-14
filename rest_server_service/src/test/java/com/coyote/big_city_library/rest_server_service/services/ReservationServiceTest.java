package com.coyote.big_city_library.rest_server_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import com.coyote.big_city_library.rest_server_model.dao.entities.ReservationId;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.BookRepository;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.ReservationRepository;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.UserRepository;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationIdDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationIdMapper;
import com.coyote.big_city_library.rest_server_service.dto.ReservationMapper;
import com.coyote.big_city_library.rest_server_service.dto.reservation.my.MyReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.reservation.my.MyReservationMapper;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;
import com.coyote.big_city_library.rest_server_service.security.JwtProvider;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    MailService mailService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    JwtProvider jwtProvider;

    @Mock
    BookRepository bookRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ReservationIdMapper reservationIdMapper;

    @Mock
    ReservationMapper reservationMapper;

    @Mock
    MyReservationMapper myReservationMapper;

    @BeforeEach
    void init() {}

    @Test
    void addReservation_returnReservationDto() throws UserAccessDeniedException, EntityExistsException {

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

    @Test
    void addReservation_throwUserAccessDeniedException_ForUser() throws UserAccessDeniedException, EntityExistsException {

        // --- ARRANGE ---
        // jwt
        when(jwtProvider.getUsername("Token")).thenReturn("OtherUser");

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

        // --- ACT / ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.addReservation(1, "Token");
        }).isInstanceOf(UserAccessDeniedException.class)
        .hasMessage("User can only add their own reservation");

        verify(jwtProvider, times(1)).getUsername("Token");
        verify(bookRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findByPseudo("OtherUser");
    }

    @Test
    void addReservation_throwUserAccessDeniedException_ForRG2() throws UserAccessDeniedException, EntityExistsException {

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
        // book reservations
        Reservation reservation1 = new Reservation();
        book.addReservation(reservation1);
        Reservation reservation2 = new Reservation();
        book.addReservation(reservation2);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        // user
        User user = new User();
        user.setId(1);
        user.setPseudo("UserTest");
        when(userRepository.findByPseudo(any())).thenReturn(Optional.of(user));

        // --- ACT / ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.addReservation(1, "Token");
        }).isInstanceOf(UserAccessDeniedException.class)
        .hasMessage("RG_Reservation_2 : Reservation list is already full");

        verify(jwtProvider, times(1)).getUsername("Token");
        verify(bookRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findByPseudo("UserTest");
    }

    @Test
    void addReservation_throwUserAccessDeniedException_ForRG3() throws UserAccessDeniedException, EntityExistsException {

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
        // user loan
        Book exemplaryBook = new Book();
        exemplaryBook.setId(1);
        Exemplary loanExemplary = new Exemplary();
        loanExemplary.setBook(exemplaryBook);
        Loan loan = new Loan();
        loan.setExemplary(loanExemplary);
        user.addLoan(loan);

        when(userRepository.findByPseudo(any())).thenReturn(Optional.of(user));

        // --- ACT / ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.addReservation(1, "Token");
        }).isInstanceOf(UserAccessDeniedException.class)
        .hasMessage("RG_Reservation_3 : Reservation of loaned book is forbidden");

        verify(jwtProvider, times(1)).getUsername("Token");
        verify(bookRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findByPseudo("UserTest");
    }

    @Test
    void addReservation_throwEntityExistsException() throws UserAccessDeniedException, EntityExistsException {

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
        when(reservationRepository.existsById(any())).thenReturn(true);

        // --- ACT / ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.addReservation(1, "Token");
        }).isInstanceOf(EntityExistsException.class)
        .hasMessage("Reservation entity with bookId:1 userId:1 already exists");

        verify(jwtProvider, times(1)).getUsername("Token");
        verify(bookRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findByPseudo("UserTest");
        verify(reservationRepository, times(1)).existsById(any());
    }

    @Test
    void findAllReservations() {

        // --- ARRANGE ---
        List<Reservation> reservations = new ArrayList<>();
        when(reservationRepository.findAll()).thenReturn(reservations);
        List<ReservationDto> reservationsDto = new ArrayList<>();
        when(reservationMapper.toDto(reservations)).thenReturn(reservationsDto);

        // --- ACT ---
        List<ReservationDto> reservationsReturned = reservationService.findAllReservations();

        // --- ASSERT ---
        verify(reservationRepository, times(1)).findAll();
        verify(reservationMapper, times(1)).toDto(reservations);
        assertThat(reservationsReturned).isNotNull();
    }

    @Test
    void findReservationById() {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(1);
        ReservationId reservationId = new ReservationId();
        reservationId.setBook(1);
        reservationId.setUser(1);
        when(reservationIdMapper.toModel(reservationIdDto)).thenReturn(reservationId);

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        ReservationDto reservationDto = new ReservationDto();
        when(reservationMapper.toDto(reservation)).thenReturn(reservationDto);

        // --- ACT ---
        ReservationDto reservationDtoReturned = reservationService.findReservationById(reservationIdDto);

        // --- ASSERT ---
        verify(reservationIdMapper, times(1)).toModel(reservationIdDto);
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationMapper, times(1)).toDto(reservation);
        assertThat(reservationDtoReturned).isNotNull();
    }

    @Test
    void findReservationsByUserPseudo() {

        // --- ARRANGE ---
        String pseudo = "Pseudo";
        List<Reservation> reservations = new ArrayList<>();
        when(reservationRepository.findByUserPseudoOrderByCreatedAtAsc(pseudo)).thenReturn(reservations);

        List<MyReservationDto> myReservationsDto = new ArrayList<>();
        when(myReservationMapper.toDto(reservations)).thenReturn(myReservationsDto);

        // --- ACT ---
        List<MyReservationDto> myReservationsReturned = reservationService.findReservationsByUserPseudo(pseudo);

        // --- ASSERT ---
        verify(reservationRepository, times(1)).findByUserPseudoOrderByCreatedAtAsc(pseudo);
        verify(myReservationMapper, times(1)).toDto(reservations);
        assertThat(myReservationsReturned).isNotNull();
    }

    @Test
    void updateReservation() {

        // --- ARRANGE ---
        ReservationDto reservationDto = new ReservationDto();

        Reservation reservation = new Reservation();
        when(reservationMapper.toModel(reservationDto)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toDto(reservation)).thenReturn(reservationDto);

        // --- ACT ---
        ReservationDto reservationDtoReturned = reservationService.updateReservation(reservationDto);

        // --- ASSERT ---
        verify(reservationMapper, times(1)).toModel(reservationDto);
        verify(reservationRepository, times(1)).save(reservation);
        verify(reservationMapper, times(1)).toDto(reservation);
        assertThat(reservationDtoReturned).isNotNull();
    }

    @Test
    void deleteReservation() {

        // --- ARRANGE ---
        ReservationDto reservationDto = new ReservationDto();
        Reservation reservation = new Reservation();
        when(reservationMapper.toModel(reservationDto)).thenReturn(reservation);

        // --- ACT ---
        reservationService.deleteReservation(reservationDto);

        // --- ASSERT ---
        verify(reservationMapper, times(1)).toModel(reservationDto);
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void deleteReservationById() throws UserAccessDeniedException {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(1);

        String token = "Token";
        String username = "Username";
        when(jwtProvider.getUsername(token)).thenReturn(username);

        User user = new User();
        user.setPseudo(username);
        when(userRepository.findById(reservationIdDto.getUserId())).thenReturn(Optional.of(user));

        ReservationId reservationId = new ReservationId();
        when(reservationIdMapper.toModel(reservationIdDto)).thenReturn(reservationId);

        // --- ACT ---
        reservationService.deleteReservationById(reservationIdDto, token);

        // --- ASSERT ---
        verify(jwtProvider, times(1)).getUsername(token);
        verify(userRepository, times(1)).findById(reservationIdDto.getUserId());
        verify(reservationIdMapper, times(1)).toModel(reservationIdDto);
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void deleteReservationById_throwUserAccessDeniedException() throws UserAccessDeniedException {

        // --- ARRANGE ---
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(1);
        reservationIdDto.setUserId(1);

        String token = "Token";
        String username = "Username";
        when(jwtProvider.getUsername(token)).thenReturn(username);

        User user = new User();
        user.setPseudo("OtherUsername");
        when(userRepository.findById(reservationIdDto.getUserId())).thenReturn(Optional.of(user));

        // --- ACT / ASSERT ---
        assertThatThrownBy(() -> {
            reservationService.deleteReservationById(reservationIdDto, token);
        }).isInstanceOf(UserAccessDeniedException.class)
          .hasMessage("User can only delete their own reservation");

        verify(jwtProvider, times(1)).getUsername(token);
        verify(userRepository, times(1)).findById(reservationIdDto.getUserId());
        verify(reservationIdMapper, times(0)).toModel(any());
        verify(reservationRepository, times(0)).deleteById(any());
    }

    @Test
    void reservationNotification_send1Mail() {

        // --- ARRANGE ---
        ReservationService reservationServiceSpy = spy(reservationService);
        // Reservation to delete
        when(reservationRepository.findByNotifiedAtNotNull()).thenReturn(null);

        // List of books
        List<Book> reservationBooks = new ArrayList<>();

        // Book with 1 reservation available
        Book reservationBook = new Book();
        Reservation reservation = new Reservation();
        Exemplary exemplary = new Exemplary();

        Book bookInReservation = new Book();
        bookInReservation.setId(1);
        reservation.setBook(bookInReservation);

        User userInReservation = new User();
        userInReservation.setId(1);
        reservation.setUser(userInReservation);

        reservationBook.addReservation(reservation);
        reservationBook.addExemplary(exemplary);
        reservationBooks.add(reservationBook);

        when(bookRepository.findDistinctByReservationsNotNull()).thenReturn(reservationBooks);

        // Reservations not notified order by createdAt
        List<Reservation> orderedReservations = new ArrayList<>();
        orderedReservations.add(reservation);
        when(reservationRepository.findByBookAndNotifiedAtIsNullOrderByCreatedAt(reservationBook)).thenReturn(
                orderedReservations);

        // --- ACT ---
        reservationServiceSpy.reservationNotification();

        // --- ASSERT ---
        verify(reservationServiceSpy, times(1)).deleteOldNotifiedReservations();
        verify(reservationRepository, times(1)).findByNotifiedAtNotNull();
        verify(reservationRepository, times(0)).delete(any());

        verify(bookRepository, times(1)).findDistinctByReservationsNotNull();
        verify(reservationServiceSpy, times(1)).isUnNotifiedReservationFromBook(reservationBook);
        verify(reservationServiceSpy, times(1)).findAvailableExemplaries(reservationBook);
        verify(reservationRepository, times(1)).findByBookAndNotifiedAtIsNullOrderByCreatedAt(reservationBook);

        verify(reservationRepository, times(1)).save(reservation);
        verify(mailService, times(1)).sendUserReservationNotification(reservation);
    }

    @Test
    void reservationNotification_send2Mails() {

        // --- ARRANGE ---
        ReservationService reservationServiceSpy = spy(reservationService);
        // Reservation to delete
        when(reservationRepository.findByNotifiedAtNotNull()).thenReturn(null);

        // List of books
        List<Book> reservationBooks = new ArrayList<>();

        // Book with 2 reservations available
        Book reservationBook = new Book();

        Reservation reservation = new Reservation();
        Reservation reservation2 = new Reservation();

        Exemplary exemplary = new Exemplary();
        exemplary.setId(1);
        Exemplary exemplary2 = new Exemplary();
        exemplary2.setId(2);

        Book bookInReservation = new Book();
        bookInReservation.setId(1);
        reservation.setBook(bookInReservation);

        User userInReservation = new User();
        userInReservation.setId(1);
        reservation.setUser(userInReservation);

        Book bookInReservation2 = new Book();
        bookInReservation2.setId(2);
        reservation2.setBook(bookInReservation2);

        User userInReservation2 = new User();
        userInReservation2.setId(2);
        reservation2.setUser(userInReservation2);


        reservationBook.addReservation(reservation);
        reservationBook.addReservation(reservation2);
        reservationBook.addExemplary(exemplary);
        reservationBook.addExemplary(exemplary2);
        reservationBooks.add(reservationBook);

        when(bookRepository.findDistinctByReservationsNotNull()).thenReturn(reservationBooks);

        // Reservations not notified order by createdAt
        List<Reservation> orderedReservations = new ArrayList<>();
        orderedReservations.add(reservation);
        orderedReservations.add(reservation2);
        when(reservationRepository.findByBookAndNotifiedAtIsNullOrderByCreatedAt(reservationBook)).thenReturn(
                orderedReservations);

        // --- ACT ---
        reservationServiceSpy.reservationNotification();

        // --- ASSERT ---
        verify(reservationServiceSpy, times(1)).deleteOldNotifiedReservations();
        verify(reservationRepository, times(1)).findByNotifiedAtNotNull();
        verify(reservationRepository, times(0)).delete(any());

        verify(bookRepository, times(1)).findDistinctByReservationsNotNull();
        verify(reservationServiceSpy, times(1)).isUnNotifiedReservationFromBook(reservationBook);
        verify(reservationServiceSpy, times(1)).findAvailableExemplaries(reservationBook);
        verify(reservationRepository, times(1)).findByBookAndNotifiedAtIsNullOrderByCreatedAt(reservationBook);

        verify(reservationRepository, times(1)).save(reservation);
        verify(reservationRepository, times(1)).save(reservation2);
        verify(mailService, times(1)).sendUserReservationNotification(reservation);
        verify(mailService, times(1)).sendUserReservationNotification(reservation2);
    }

    @Test
    void deleteOldNotifiedReservations_given2Reservations_deleteOnlyTheOnePast48h() {

        // --- ARRANGE ---
        List<Reservation> notifiedReservations = new ArrayList<>();

        // reservation with recent notification (<48h):
        Reservation reservationToKeep = new Reservation();
        reservationToKeep.setNotifiedAt(ZonedDateTime.now(ZoneId.of("UTC")).minusHours(24));
        notifiedReservations.add(reservationToKeep);

        // reservation with old notification (>48h) :
        Reservation reservationToDelete = new Reservation();
        reservationToDelete.setNotifiedAt(ZonedDateTime.now(ZoneId.of("UTC")).minusHours(48));
        Book reservationBookToDelete = new Book();
        reservationBookToDelete.setId(1);
        reservationToDelete.setBook(reservationBookToDelete);
        User reservationUserToDelete = new User();
        reservationUserToDelete.setId(1);
        reservationToDelete.setUser(reservationUserToDelete);
        reservationToDelete.setExemplary(new Exemplary());
        notifiedReservations.add(reservationToDelete);

        when(reservationRepository.findByNotifiedAtNotNull()).thenReturn(notifiedReservations);

        // --- ACT ---
        reservationService.deleteOldNotifiedReservations();

        // --- ASSERT ---
        verify(reservationRepository, times(1)).findByNotifiedAtNotNull();
        verify(reservationRepository, times(0)).delete(reservationToKeep);
        verify(reservationRepository, times(1)).delete(reservationToDelete);
    }

    @Test
    void isUnNotifiedReservationFromBook_returnsTrue() {

        // --- ARRANGE ---
        Book book = new Book();
        // notified
        Reservation reservationNotified = new Reservation();
        reservationNotified.setNotifiedAt(ZonedDateTime.now(ZoneId.of("UTC")).minusHours(24));
        book.addReservation(reservationNotified);
        // not notified
        Reservation reservationNotNotified = new Reservation();
        book.addReservation(reservationNotNotified);

        // --- ACT ---
        boolean returned = reservationService.isUnNotifiedReservationFromBook(book);

        // --- ASSERT ---
        assertThat(returned).isTrue();
    }

    @Test
    void isUnNotifiedReservationFromBook_returnsFalse() {

        // --- ARRANGE ---
        Book book = new Book();
        // notified
        Reservation reservationNotified = new Reservation();
        reservationNotified.setNotifiedAt(ZonedDateTime.now(ZoneId.of("UTC")).minusHours(24));
        book.addReservation(reservationNotified);
        // not notified
        Reservation reservationNotified2 = new Reservation();
        reservationNotified2.setNotifiedAt(ZonedDateTime.now(ZoneId.of("UTC")).minusHours(42));
        book.addReservation(reservationNotified2);

        // --- ACT ---
        boolean returned = reservationService.isUnNotifiedReservationFromBook(book);

        // --- ASSERT ---
        assertThat(returned).isFalse();
    }

    @Test
    void findAvailableExemplaries_given4_returns2() {

        // --- ARRANGE ---
        Book book = new Book();
        // exemplary with reservation
        Exemplary exemplaryWithReserv = new Exemplary();
        Reservation reservation = new Reservation();
        exemplaryWithReserv.setReservation(reservation);
        book.addExemplary(exemplaryWithReserv);
        // exemplary without reservation without loans
        Exemplary exemplaryNoReservNoLoan = new Exemplary();
        book.addExemplary(exemplaryNoReservNoLoan);
        // exemplary without reservation with loan returned
        Exemplary exemplaryNoReservWithLoanReturned = new Exemplary();
        Loan loanReturned = new Loan();
        loanReturned.setReturnDate(LocalDate.now());
        exemplaryNoReservWithLoanReturned.addLoan(loanReturned);
        book.addExemplary(exemplaryNoReservWithLoanReturned);
        // exemplary without reservation with loan not returned
        Exemplary exemplaryNoReservWithLoanNotReturned = new Exemplary();
        Loan loanNotReturned = new Loan();
        exemplaryNoReservWithLoanNotReturned.addLoan(loanNotReturned);
        book.addExemplary(exemplaryNoReservWithLoanNotReturned);

        // --- ACT ---
        List<Exemplary> returnedExemplaries = reservationService.findAvailableExemplaries(book);

        // --- ASSERT ---
        assertThat(returnedExemplaries).hasSize(2)
                                       .containsExactly(
                                               exemplaryNoReservNoLoan,
                                               exemplaryNoReservWithLoanReturned);
    }


    @Test
    void isReturnedExemplary_returnsTrue() {

        // --- ARRANGE ---
        Exemplary exemplary = new Exemplary();
        // returned loans
        Loan loanReturned = new Loan();
        loanReturned.setReturnDate(LocalDate.now());
        exemplary.addLoan(loanReturned);
        exemplary.addLoan(loanReturned);

        // --- ACT ---
        boolean returned = reservationService.isReturnedExemplary(exemplary);

        // --- ASSERT ---
        assertThat(returned).isTrue();
    }

    @Test
    void isReturnedExemplary_returnsFalse() {

        // --- ARRANGE ---
        Exemplary exemplary = new Exemplary();
        // returned loan
        Loan loanReturned = new Loan();
        loanReturned.setReturnDate(LocalDate.now());
        exemplary.addLoan(loanReturned);
        // not returned loan
        Loan loanNotReturned = new Loan();
        exemplary.addLoan(loanNotReturned);

        // --- ACT ---
        boolean returned = reservationService.isReturnedExemplary(exemplary);

        // --- ASSERT ---
        assertThat(returned).isFalse();
    }

}
