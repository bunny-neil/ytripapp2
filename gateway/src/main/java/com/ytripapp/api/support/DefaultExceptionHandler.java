package com.ytripapp.api.support;

import com.ytripapp.api.security.AuthenticationRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@ControllerAdvice
public class DefaultExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(AuthenticationRequiredException.class)
    public ResponseEntity<AuthenticationRequiredException> handle(AuthenticationRequiredException ex, Locale locale) {
        ex.setDetails(messageSource.getMessage(ex.getCode(), null, locale));
        return new ResponseEntity<>(ex, HttpStatus.UNAUTHORIZED);
    }
}
