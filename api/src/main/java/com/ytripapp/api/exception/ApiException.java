package com.ytripapp.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"cause","stackTrace", "suppressed", "localizedMessage"})
public abstract class ApiException extends RuntimeException {

    private static final long serialVersionUID = -463425769442535915L;

    public ApiException(String message) {
        super(message);
    }
}
