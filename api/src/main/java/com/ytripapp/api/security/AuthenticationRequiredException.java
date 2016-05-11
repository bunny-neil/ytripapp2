package com.ytripapp.api.security;

import com.ytripapp.api.exception.ApiException;

public class AuthenticationRequiredException extends ApiException {

    private static final long serialVersionUID = -5301912803270854500L;

    public AuthenticationRequiredException() {
        super("required.authentication");
    }
}
