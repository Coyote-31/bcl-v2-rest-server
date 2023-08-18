package com.coyote.big_city_library.rest_server_controller.controllers;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.coyote.big_city_library.rest_server_service.exceptions.LoanOverdueException;
import com.coyote.big_city_library.rest_server_service.exceptions.UserAccessDeniedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(LoanOverdueException.class)
    public ResponseEntity<String> handleLoanOverdueException(LoanOverdueException e) {

        log.warn("handleLoanOverdueException => reponseStatus=FORBIDDEN, message='{}'", e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(e.getMessage());
    }

    @ExceptionHandler(UserAccessDeniedException.class)
    public ResponseEntity<String> handleUserAccessDeniedException(UserAccessDeniedException e) {

        log.warn("handleUserAccessDeniedException => reponseStatus=FORBIDDEN, message='{}'", e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {

        log.warn("handleNoSuchElementException => reponseStatus=NOT_FOUND, message='{}'", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

}
