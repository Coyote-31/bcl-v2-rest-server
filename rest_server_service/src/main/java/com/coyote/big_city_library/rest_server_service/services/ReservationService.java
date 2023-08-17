package com.coyote.big_city_library.rest_server_service.services;

import java.text.MessageFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import com.coyote.big_city_library.rest_server_model.dao.entities.ReservationId;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.BookRepository;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.ReservationRepository;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.UserRepository;
import com.coyote.big_city_library.rest_server_service.dto.BookMapper;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationIdDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationIdMapper;
import com.coyote.big_city_library.rest_server_service.dto.ReservationMapper;
import com.coyote.big_city_library.rest_server_service.dto.UserMapper;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;
import com.coyote.big_city_library.rest_server_service.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class handling reservations
 *
 * @see ReservationRepository
 */
@Slf4j
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected ReservationIdMapper reservationIdMapper;

    @Autowired
    protected ReservationMapper reservationMapper;

    @Autowired
    protected BookMapper bookMapper;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected MailService mailService;

    @Autowired
    protected JwtProvider jwtProvider;


    /**
     * Adds a new reservation.
     *
     * <ul>
     * <li><strong>RG_Reservation_1</strong> : Tous les types d’ouvrages peuvent être réservés.</li>
     * <li><strong>RG_Reservation_2</strong> : La liste de réservation ne peut comporter qu’un maximum de personnes correspondant à 2x le nombre d’exemplaires de l’ouvrage.</li>
     * <li><strong>RG_Reservation_3</strong> : Il n’est pas possible pour un usager de réserver un ouvrage qu’il a déjà en cours d’emprunt</li>
     * </ul>
     *
     * @param reservationIdDto : DTO carring BookId and UserId
     * @param token
     * @return reservationDto
     * @throws UserAccessDeniedException
     * @see ReservationIdDto
     * @see ReservationDto
     * @see Reservation
     */
    public ReservationDto addReservation(ReservationIdDto reservationIdDto, String token)
            throws UserAccessDeniedException {

        // Exctract user from JWT
        String tokenUser = jwtProvider.getUsername(token);
        log.debug("User name : {}", tokenUser);

        // Create Book entity
        Book book = bookRepository.findById(reservationIdDto.getBookId()).orElseThrow();

        // Create User entity
        User user = userRepository.findById(reservationIdDto.getUserId()).orElseThrow();

        // Verify user from JWT is the reservation user
        if (!tokenUser.equals(user.getPseudo())) {
            throw new UserAccessDeniedException("Jwt user is different from reservation user");
        }

        // RG_Reservation_2
        log.debug("RG_Reservation_2 => exemplaries_x2:{} reservations:{}",
                book.getExemplaries().size() * 2,
                book.getReservations().size());
        if (book.getReservations().size() >= book.getExemplaries().size() * 2) {
            throw new UserAccessDeniedException("RG_Reservation_2 : Reservation list is already full");
        }

        // RG_Reservation_3
        for (Loan loan : user.getLoans()) {
            if (loan.getExemplary().getBook().getId().equals(book.getId())
                    && loan.getReturnDate() == null) {
                throw new UserAccessDeniedException("RG_Reservation_3 : Reservation of loaned book is forbidden");
            }
        }

        // Create ZonedDateTime createdAt
        ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("UTC"));

        // Construct Reservation entity
        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setCreatedAt(createdAt);

        // Persist
        ReservationId reservationId = new ReservationId();
        reservationId.setBook(reservationIdDto.getBookId());
        reservationId.setUser(reservationIdDto.getUserId());

        if (!reservationRepository.existsById(reservationId)) {
            reservation = reservationRepository.save(reservation);
        } else {
            String message = "Reservation entity with bookId:{0} userId:{1} already exists";
            String messageFormatted = MessageFormat.format(
                    message,
                    reservationIdDto.getBookId(),
                    reservationIdDto.getUserId());
            throw new EntityExistsException(messageFormatted);
        }

        return reservationMapper.toDto(reservation);
    }

    /**
     * Returns a list of all the reservations.
     *
     * @return All the reservations.
     * @see Reservation
     * @see ReservationDto
     */
    public List<ReservationDto> findAllReservations() {
        return reservationMapper.toDto(reservationRepository.findAll());
    }

    /**
     * Returns a reservation with a given composite PK {@link ReservationId}.
     * Wich contains bookId and userId as Integer.
     *
     * @param reservationId
     * @return The reservationDto with the given id or null if none found.
     * @see ReservationId
     * @see ReservationIdDto
     * @see Reservation
     * @see ReservationDto
     */
    public ReservationDto findReservationById(ReservationIdDto reservationIdDto) {
        ReservationId reservationId = reservationIdMapper.toModel(reservationIdDto);
        return reservationMapper.toDto(reservationRepository.findById(reservationId).orElse(null));
    }

    /**
     * Updates a given reservation.
     *
     * @param reservationDto to update.
     * @return The updated reservation; will never be null.
     * @see Reservation
     * @see ReservationDto
     */
    public ReservationDto updateReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.toModel(reservationDto);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    /**
     * Deletes a given reservation.
     *
     * @param reservationDto to delete.
     * @see Reservation
     * @see ReservationDto
     */
    public void deleteReservation(ReservationDto reservationDto) {
        reservationRepository.delete(reservationMapper.toModel(reservationDto));
    }

    /**
     * Deletes a reservation with a composite PK {@link ReservationId}.
     * Wich contains bookId and userId as Integer.
     *
     * @param reservationIdDto
     * @param token
     * @throws UserAccessDeniedException
     * @see Reservation
     * @see ReservationDto
     */
    public void deleteReservationById(ReservationIdDto reservationIdDto, String token)
            throws UserAccessDeniedException {

        // Exctract user from JWT
        String tokenUser = jwtProvider.getUsername(token);
        log.debug("User name : {}", tokenUser);

        // get the User entity
        User user = userRepository.findById(reservationIdDto.getUserId()).orElseThrow();

        // Verify user from JWT is the reservation user
        if (!tokenUser.equals(user.getPseudo())) {
            throw new UserAccessDeniedException("Jwt user is different from reservation user");
        }

        ReservationId reservationId = reservationIdMapper.toModel(reservationIdDto);
        reservationRepository.deleteById(reservationId);
    }

    /**
     * Delete reservation notified more than 48h ago,
     * Send mail to reservations with exemplary available
     */
    public void reservationNotification() {

        // Delete reservations with notification older than 48h
        List<Reservation> notifiedReservations = reservationRepository.findByNotifiedAtNotNull();

        for (Reservation reservation : notifiedReservations) {
            if (reservation.getNotifiedAt().plusHours(47).isBefore(ZonedDateTime.now(ZoneId.of("UTC")))) {
                reservationRepository.delete(reservation);
                log.debug("Reservation notification older than 48h deleted => bookId:{} userId:{}",
                        reservation.getBook().getId(),
                        reservation.getUser().getId());
            }
        }

        // Send mail to users with reservation available :

        // Find books with reservations
        List<Book> reservationBooks = bookRepository.findDistinctByReservationsNotNull();
        log.debug("Find {} book(s) with reservation", reservationBooks.size());

        for (Book book : reservationBooks) {
            log.debug("Find book with reservation => BookId:{}", book.getId());

            // Check if their is un-notified reservation
            boolean isUnNotified = false;

            for (Reservation reservation : book.getReservations()) {
                if (reservation.getNotifiedAt() == null) {
                    isUnNotified = true;
                }
            }

            // Go to the next book if their is no unNotified reservation
            if (Boolean.FALSE.equals(isUnNotified)) {
                log.debug("No unNotified reservation find for this book => BookId:{}", book.getId());
                continue;
            }

            // Find available exemplaries of the book
            List<Exemplary> availableExemplaries = new ArrayList<>();

            for (Exemplary exemplary : book.getExemplaries()) {

                // Not currently under reservation
                if (exemplary.getReservation() == null) {

                    // Check if returned by searching returnDate in loans
                    if (exemplary.getLoans() != null && !exemplary.getLoans().isEmpty()) {

                        boolean returned = true;

                        for (Loan loan : exemplary.getLoans()) {
                            if (loan.getReturnDate() == null) {
                                returned = false;
                            }
                        }

                        if (returned) {
                            availableExemplaries.add(exemplary);
                        }

                        // Without loans
                    } else {
                        availableExemplaries.add(exemplary);
                    }

                }
            }

            // If any available exemplary
            if (!availableExemplaries.isEmpty()) {

                // Get reservations of the book without notifications ordered by createdAt
                List<Reservation> sortedAvailableReservations =
                        reservationRepository.findByBookAndNotifiedAtIsNullOrderByCreatedAt(book);

                // debug verify sorting result
                int count = 0;
                for (Reservation reservation : sortedAvailableReservations) {
                    count++;
                    log.debug("Reservation #{} => bookId:{} userId:{}",
                            count,
                            reservation.getBook().getId(),
                            reservation.getUser().getId());
                }

                // Assign availableExemplary to the next availableReservation
                for (int i = 0; i < availableExemplaries.size(); i++) {

                    Integer reservationMaxIndex = sortedAvailableReservations.size() - 1;

                    if (i <= reservationMaxIndex) {

                        // Update the reservation
                        Reservation reservation = sortedAvailableReservations.get(i);
                        reservation.setNotifiedAt(ZonedDateTime.now(ZoneId.of("UTC")));
                        reservation.setExemplary(availableExemplaries.get(i));

                        // Persist the reservation
                        reservationRepository.save(reservation);

                        // Mail the user
                        mailService.sendUserReservationNotification(reservation);
                        log.debug("Mail to userId:{} for bookId:{} exemplaryId:{}",
                                reservation.getUser().getId(),
                                book.getId(),
                                availableExemplaries.get(i).getId());

                    } else {
                        break;
                    }
                }

            } else {
                log.debug("No available exemplary for bookId:{}", book.getId());
            }
        }

    }

}
