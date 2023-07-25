package com.coyote.big_city_library.rest_server_controller.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.coyote.big_city_library.rest_server_service.dto.BookDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.UserDto;
import com.coyote.big_city_library.rest_server_service.services.ReservationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping("/add")
    public ReservationDto addReservation(@Valid @RequestBody ReservationDto reservationDto) {
        ReservationDto reservationSaved = reservationService.addReservation(reservationDto);
        log.debug("addReservation() => reservation for book:{} and user:{} added",
                reservationSaved.getBook().getTitle(),
                reservationSaved.getUser().getPseudo());
        return reservationSaved;
    }

    @GetMapping("")
    public List<ReservationDto> findAllReservations() {
        List<ReservationDto> reservations = reservationService.findAllReservations();
        log.debug("findAllReservations() => {} reservation(s) found", reservations.size());
        return reservations;
    }

    // TODO : handle composite ID with ReservationId or book.id + user.id
    @GetMapping("/{bookid}")
    public ReservationDto findReservationByIdBookAndUser(
            @RequestParam Integer userId,
            @PathVariable Integer id,
            @Valid @RequestBody BookDto bookDto,
            @Valid @RequestBody UserDto userDto) {

        ReservationDto reservationDto = reservationService.findReservationByIdBookAndUser(bookDto, userDto);
        if (reservationDto != null) {
            log.debug("findReservationByIdBookAndUser() => reservation with book:{} and user:{} found",
                    reservationDto.getBook().getTitle(),
                    reservationDto.getUser().getPseudo());
        } else {
            log.debug("findReservationByIdBookAndUser() => No Reservation found with book:{} and user:{} found",
                    bookDto.getTitle(),
                    userDto.getPseudo());
        }

        return reservationDto;
    }

    @PutMapping("/update")
    public ReservationDto updateReservation(@Valid @RequestBody ReservationDto reservationDto) {
        ReservationDto reservationUpdated = reservationService.updateReservation(reservationDto);
        log.debug("updateReservation() => reservation with with book:{} and user:{} updated",
                reservationUpdated.getBook().getTitle(),
                reservationUpdated.getUser().getPseudo());
        return reservationUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteReservation(@Valid @RequestBody ReservationDto reservationDto) {
        reservationService.deleteReservation(reservationDto);
        log.debug("deleteReservation() => reservation with with book:{} and user:{} removed",
                reservationDto.getBook().getTitle(),
                reservationDto.getUser().getPseudo());
    }

    // TODO : handle composite ID with ReservationId or book.id + user.id
    @DeleteMapping("/delete/{id}")
    public void deleteReservationById(
            @PathVariable Integer id,
            @Valid @RequestBody BookDto bookDto,
            @Valid @RequestBody UserDto userDto) {

        reservationService.deleteReservationByIdBookAndUser(bookDto, userDto);
        log.debug("deleteReservationById() => reservation with with book:{} and user:{} removed",
                bookDto.getTitle(),
                userDto.getPseudo());
    }

}
