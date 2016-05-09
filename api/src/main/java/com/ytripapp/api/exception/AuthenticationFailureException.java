package com.ytripapp.api.exception;

public class AuthenticationFailureException extends ApiException {

    private static final long serialVersionUID = 3551478134906629510L;

    public AuthenticationFailureException(String message) {
        super(message);
    }
}
