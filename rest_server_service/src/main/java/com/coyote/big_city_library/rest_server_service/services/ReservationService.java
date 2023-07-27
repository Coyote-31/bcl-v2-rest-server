package com.coyote.big_city_library.rest_server_service.services;

import java.text.MessageFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
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
import com.coyote.big_city_library.rest_server_service.security.JwtProvider;
import io.jsonwebtoken.JwtException;
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
    protected JwtProvider jwtProvider;


    /**
     * Adds a new reservation.
     *
     * @param reservationIdDto : DTO carring BookId and UserId
     * @param token
     * @return reservationDto
     * @see ReservationIdDto
     * @see ReservationDto
     * @see Reservation
     */
    public ReservationDto addReservation(ReservationIdDto reservationIdDto, String token) {

        // Exctract user from JWT
        String tokenUser = jwtProvider.getUsername(token);
        log.debug("User name : {}", tokenUser);

        // TODO add RG 1, 2 and 3

        // Create Book entity
        Book book = bookRepository.findById(reservationIdDto.getBookId()).orElseThrow();

        // Create User entity
        User user = userRepository.findById(reservationIdDto.getUserId()).orElseThrow();

        // Verify user from JWT is the reservation user
        if (!tokenUser.equals(user.getPseudo())) {
            throw new JwtException("Jwt user is different from reservation user");
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
    public ReservationDto findReservationById(ReservationId reservationId) {
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
     * @see Reservation
     * @see ReservationDto
     */
    public void deleteReservationById(ReservationIdDto reservationIdDto, String token) {

        // Exctract user from JWT
        String tokenUser = jwtProvider.getUsername(token);
        log.debug("User name : {}", tokenUser);

        // get the User entity
        User user = userRepository.findById(reservationIdDto.getUserId()).orElseThrow();

        // Verify user from JWT is the reservation user
        if (!tokenUser.equals(user.getPseudo())) {
            throw new JwtException("Jwt user is different from reservation user");
        }

        ReservationId reservationId = reservationIdMapper.toModel(reservationIdDto);
        reservationRepository.deleteById(reservationId);
    }

}
