package com.ytripapp.api.security;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    private MappingJackson2HttpMessageConverter messageConverter;

    public AuthenticationFailureHandler(MappingJackson2HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        messageConverter.write(
            new AuthenticationFailureException(exception.getMessage()),
            MediaType.APPLICATION_JSON_UTF8,
            new ServletServerHttpResponse(response)
        );
    }
}
