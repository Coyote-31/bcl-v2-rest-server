package com.coyote.big_city_library.rest_server_controller.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.coyote.big_city_library.rest_server_service.dto.ReservationDto;
import com.coyote.big_city_library.rest_server_service.dto.ReservationIdDto;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;
import com.coyote.big_city_library.rest_server_service.services.ReservationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping("/add")
    public ResponseEntity<ReservationDto> addReservation(
            @Valid @RequestBody ReservationIdDto reservationIdDto,
            @RequestHeader(name = "Authorization") String token) {
        try {
            ReservationDto reservationSaved = reservationService.addReservation(reservationIdDto, token);
            log.debug("addReservation() => reservation for book:{} and user:{} added",
                    reservationSaved.getBook().getTitle(),
                    reservationSaved.getUser().getPseudo());
            return ResponseEntity.ok(reservationSaved);

        } catch (UserAccessDeniedException e) {
            log.warn("UserAccessDeniedException : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("")
    public List<ReservationDto> findAllReservations() {
        List<ReservationDto> reservations = reservationService.findAllReservations();
        log.debug("findAllReservations() => {} reservation(s) found", reservations.size());
        return reservations;
    }

    @GetMapping("/reservation")
    public ResponseEntity<ReservationDto> findReservationById(
            @RequestParam Integer bookId,
            @RequestParam Integer userId) {

        // Build the ID entity
        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(bookId);
        reservationIdDto.setUserId(userId);

        // Get entity from repository
        ReservationDto reservationDto = reservationService.findReservationById(reservationIdDto);
        if (reservationDto != null) {
            log.debug("findReservationById() => reservation with book:{} and user:{} found",
                    reservationDto.getBook().getTitle(),
                    reservationDto.getUser().getPseudo());
            return ResponseEntity.ok(reservationDto);

        } else {
            log.debug("findReservationById() => No Reservation found with bookId:{} and userId:{}",
                    reservationIdDto.getBookId(),
                    reservationIdDto.getUserId());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ReservationDto updateReservation(@Valid @RequestBody ReservationDto reservationDto) {
        ReservationDto reservationUpdated = reservationService.updateReservation(reservationDto);
        log.debug("updateReservation() => reservation with book:{} and user:{} updated",
                reservationUpdated.getBook().getTitle(),
                reservationUpdated.getUser().getPseudo());
        return reservationUpdated;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReservationById(
            @RequestParam Integer bookId,
            @RequestParam Integer userId,
            @RequestHeader(name = "Authorization") String token) {

        ReservationIdDto reservationIdDto = new ReservationIdDto();
        reservationIdDto.setBookId(bookId);
        reservationIdDto.setUserId(userId);

        try {
            reservationService.deleteReservationById(reservationIdDto, token);
            log.debug("deleteReservation() => reservation with bookId:{} and userId:{} removed",
                    reservationIdDto.getBookId(),
                    reservationIdDto.getUserId());
            return ResponseEntity.ok().build();

        } catch (UserAccessDeniedException e) {
            log.warn("UserAccessDeniedException : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
