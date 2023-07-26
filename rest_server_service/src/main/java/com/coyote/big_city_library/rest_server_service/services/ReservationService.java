package com.coyote.big_city_library.rest_server_service.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityExistsException;
import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coyote.big_city_library.rest_server_model.dao.entities.Book;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import com.coyote.big_city_library.rest_server_model.dao.entities.ReservationId;
import com.coyote.big_city_library.rest_server_model.dao.entities.User;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.BookRepository;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.ReservationRepository;
import com.coyote.big_city_library.rest_server_repository.dao.repositories.UserRepository;
import com.coyote.big_city_library.rest_server_service.dto.BookDto;
import com.coyote.big_city_library.rest_server_service.dto.BookMapper;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationMapper;
import com.coyote.big_city_library.rest_server_service.dto.UserDto;
import com.coyote.big_city_library.rest_server_service.dto.UserMapper;
import com.coyote.big_city_library.rest_server_service.dto.reservation.CreateReservationDto;

/**
 * Service class handling reservations
 *
 * @see ReservationRepository
 */
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected ReservationMapper reservationMapper;

    @Autowired
    protected BookMapper bookMapper;

    @Autowired
    protected UserMapper userMapper;


    /**
     * Adds a new reservation.
     *
     * @param createReservationDto : DTO carring BookId and UserId
     * @return reservationDto
     * @see CreateReservationDto
     * @see ReservationDto
     * @see Reservation
     */
    public ReservationDto addReservation(CreateReservationDto createReservationDto) {

        // TODO add RG 1, 2 and 3

        // Create Book entity
        Book book = bookRepository.findById(createReservationDto.getBookId()).orElseThrow();

        // Create User entity
        User user = userRepository.findById(createReservationDto.getUserId()).orElseThrow();

        // Create ZonedDateTime createdAt
        ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("UTC"));

        // Construct Reservation entity
        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setCreatedAt(createdAt);

        // Persist
        ReservationId reservationId = new ReservationId();
        reservationId.setBook(createReservationDto.getBookId());
        reservationId.setUser(createReservationDto.getUserId());

        if (!reservationRepository.existsById(reservationId)) {
            reservation = reservationRepository.save(reservation);
        } else {
            String message = "Reservation entity with bookId:{0} userId:{1} already exists";
            String messageFormatted = MessageFormat.format(
                    message,
                    createReservationDto.getBookId(),
                    createReservationDto.getUserId());
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
     * Returns a reservation with a given composite PK ({@link BookDto} + {@link UserDto}).
     *
     * @param bookDto
     * @param userDto
     * @return The reservation with the given id or null if none found.
     * @see Reservation
     * @see ReservationDto
     * @see BookDto
     * @see UserDto
     */
    // TODO remplacer  par Integer IDs
    public ReservationDto findReservationByIdBookAndUser(BookDto bookDto, UserDto userDto) {
        ReservationId reservationId = new ReservationId();
        reservationId.setBook(bookDto.getId());
        reservationId.setUser(userDto.getId());
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
     * Deletes a reservation with a composite PK ({@link BookDto} + {@link UserDto}).
     *
     * @param bookDto
     * @param userDto
     * @see Reservation
     * @see ReservationDto
     */
    public void deleteReservationByIdBookAndUser(BookDto bookDto, UserDto userDto) {
        ReservationId reservationId = new ReservationId();
        reservationId.setBook(bookDto.getId());
        reservationId.setUser(userDto.getId());
        reservationRepository.deleteById(reservationId);
    }

}
