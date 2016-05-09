package com.ytripapp.api.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause","stackTrace", "suppressed"})
public class AuthenticationFailureException extends RuntimeException {

    private static final long serialVersionUID = 3551478134906629510L;

    public AuthenticationFailureException(String message) {
        super(message);
    }
}
