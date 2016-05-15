package com.ytripapp.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"stackTrace", "message", "cause", "localizedMessage", "suppressed"})
public class GatewayException extends RuntimeException {

    private static final long serialVersionUID = 1777572669985557247L;

    private String code;
    private String details;

    public GatewayException(String code) {
        this.code = code;
    }
}
