package com.coyote.big_city_library.rest_server_service.exceptions;

public class LoanOverdueException extends Exception {
    public LoanOverdueException(String errorMessage) {
        super(errorMessage);
    }
}
