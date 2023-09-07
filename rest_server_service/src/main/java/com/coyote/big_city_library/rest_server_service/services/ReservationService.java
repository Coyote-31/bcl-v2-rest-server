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
import com.coyote.big_city_library.rest_server_service.dto.reservation.my.MyReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.reservation.my.MyReservationMapper;
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
    protected MyReservationMapper myReservationMapper;

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
    public ReservationDto addReservation(Integer bookId, String token)
            throws UserAccessDeniedException {

        // Exctract user from JWT
        String tokenUser = jwtProvider.getUsername(token);
        log.debug("User name : {}", tokenUser);

        // Create Book entity
        Book book = bookRepository.findById(bookId).orElseThrow();

        // Create User entity
        User user = userRepository.findByPseudo(tokenUser).orElseThrow();

        // Verify user from JWT is the reservation user
        if (!tokenUser.equals(user.getPseudo())) {
            log.warn("UserAccessDeniedException : User can only add their own reservation");
            throw new UserAccessDeniedException("User can only add their own reservation");
        }

        // RG_Reservation_2
        log.debug("RG_Reservation_2 => exemplaries_x2:{} reservations:{}",
                book.getExemplaries().size() * 2,
                book.getReservations().size());
        if (book.getReservations().size() >= book.getExemplaries().size() * 2) {
            log.warn("UserAccessDeniedException : RG_Reservation_2 => Reservation list is already full");
            throw new UserAccessDeniedException("RG_Reservation_2 : Reservation list is already full");
        }

        // RG_Reservation_3
        for (Loan loan : user.getLoans()) {
            if (loan.getExemplary().getBook().getId().equals(book.getId())
                    && loan.getReturnDate() == null) {
                log.warn("UserAccessDeniedException : RG_Reservation_3 : Reservation of loaned book is forbidden");
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
        reservationId.setBook(book.getId());
        reservationId.setUser(user.getId());

        if (!reservationRepository.existsById(reservationId)) {
            reservation = reservationRepository.save(reservation);
        } else {
            String message = "Reservation entity with bookId:{0} userId:{1} already exists";
            String messageFormatted = MessageFormat.format(
                    message,
                    book.getId(),
                    user.getId());
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
     * Returns all reservations owned by a given user's pseudo
     *
     * @param pseudo of the user
     * @return The reservations list or null if none found.
     */
    public List<MyReservationDto> findReservationsByUserPseudo(String pseudo) {
        return myReservationMapper.toDto(reservationRepository.findByUserPseudoOrderByCreatedAtAsc(pseudo));
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
            log.warn("UserAccessDeniedException : User can only delete their own reservation");
            throw new UserAccessDeniedException("User can only delete their own reservation");
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
        deleteOldNotifiedReservations();

        // Send mail to users with reservation available :

        // Find books with reservations
        List<Book> reservationBooks = bookRepository.findDistinctByReservationsNotNull();
        log.debug("Find {} book(s) with reservation", reservationBooks.size());

        for (Book book : reservationBooks) {
            log.debug("Find book with reservation => BookId:{}", book.getId());

            // Go to the next book if their is no unNotified reservation
            if (Boolean.FALSE.equals(isUnNotifiedReservationFromBook(book))) {
                log.debug("No unNotified reservation find for this book => BookId:{}", book.getId());
                continue;
            }

            // Find available exemplaries of the book
            List<Exemplary> availableExemplaries = findAvailableExemplaries(book);

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


    /**
     * Delete reservations with notification older than 48h
     */
    public void deleteOldNotifiedReservations() {

        List<Reservation> notifiedReservations = reservationRepository.findByNotifiedAtNotNull();

        if (notifiedReservations == null) {
            return;
        }

        for (Reservation reservation : notifiedReservations) {
            if (reservation.getNotifiedAt().plusHours(47).isBefore(ZonedDateTime.now(ZoneId.of("UTC")))) {
                if (reservation.getExemplary() != null) {
                    reservation.getExemplary().setReservation(null);
                }
                reservationRepository.delete(reservation);
                log.debug("Reservation notification older than 48h deleted => bookId:{} userId:{}",
                        reservation.getBook().getId(),
                        reservation.getUser().getId());
            }
        }
    }

    /**
     * Check if their is un-notified reservation of this book
     *
     * @param book
     * @return True if their is un-notified reservation of this book
     *
     * @see {@link Book}
     */
    public boolean isUnNotifiedReservationFromBook(Book book) {

        boolean isUnNotified = false;

        for (Reservation reservation : book.getReservations()) {
            if (reservation.getNotifiedAt() == null) {
                isUnNotified = true;
            }
        }
        return isUnNotified;
    }


    /**
     * Find available exemplaries of this book
     *
     * @param book
     * @return List of available exemplaries
     *
     * @see {@link Book}
     */
    public List<Exemplary> findAvailableExemplaries(Book book) {
        // Find available exemplaries of the book
        List<Exemplary> availableExemplaries = new ArrayList<>();

        for (Exemplary exemplary : book.getExemplaries()) {

            // if currently under reservation
            if (exemplary.getReservation() != null)
                continue;

            // If their is loans check if returned
            if (exemplary.getLoans() != null && !exemplary.getLoans().isEmpty()) {

                if (isReturnedExemplary(exemplary)) {
                    availableExemplaries.add(exemplary);
                }

                // Without loans
            } else {
                availableExemplaries.add(exemplary);
            }

        }

        return availableExemplaries;
    }

    /**
     * Check if this exemplary is returned by searching returnDate in loans
     *
     * @param exemplary
     * @return true if the exemplary is already returned
     *
     * @see {@link Exemplary}
     */
    public boolean isReturnedExemplary(Exemplary exemplary) {

        boolean returned = true;

        for (Loan loan : exemplary.getLoans()) {
            if (loan.getReturnDate() == null) {
                returned = false;
            }
        }

        return returned;
    }

}
