package com.ytripapp.api.security;

import com.ytripapp.api.exception.ApiException;

public class AuthenticationFailureException extends ApiException {

    public AuthenticationFailureException() {
        super("failure.authentication");
    }
}
