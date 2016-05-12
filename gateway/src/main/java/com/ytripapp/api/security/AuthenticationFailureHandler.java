package com.ytripapp.api.security;

import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    private MessageSource messageSource;
    private LocaleResolver localeResolver;
    private MappingJackson2HttpMessageConverter messageConverter;

    public AuthenticationFailureHandler(MessageSource messageSource,
                                        LocaleResolver localeResolver,
                                        MappingJackson2HttpMessageConverter messageConverter) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
        this.messageConverter = messageConverter;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        AuthenticationFailureException ex = new AuthenticationFailureException();
        ex.setDetails(messageSource.getMessage(ex.getCode(), null, localeResolver.resolveLocale(request)));
        response.setStatus(401);
        messageConverter.write(ex, MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
    }
}
