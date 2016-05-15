package com.ytripapp.api.security;

import com.ytripapp.api.exception.GatewayException;

public class AuthenticationRequiredException extends GatewayException {

    private static final long serialVersionUID = -5301912803270854500L;

    public AuthenticationRequiredException() {
        super("required.authentication");
    }
}
