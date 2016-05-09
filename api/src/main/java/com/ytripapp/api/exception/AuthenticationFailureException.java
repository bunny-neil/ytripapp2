package com.ytripapp.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationFailureException extends ApiException {

    private static final long serialVersionUID = 3551478134906629510L;

    public AuthenticationFailureException(String message) {
        super(message);
    }
}
