package com.ytripapp.api.security;

import com.ytripapp.api.exception.GatewayException;

public class AuthenticationFailureException extends GatewayException {

    public AuthenticationFailureException() {
        super("failure.authentication");
    }
}
