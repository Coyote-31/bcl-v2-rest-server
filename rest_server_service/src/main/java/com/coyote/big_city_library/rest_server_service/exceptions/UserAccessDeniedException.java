package com.coyote.big_city_library.rest_server_service.exceptions;

public class UserAccessDeniedException extends Exception {
    public UserAccessDeniedException(String errorMessage) {
        super(errorMessage);
    }
}
