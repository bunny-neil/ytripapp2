package com.ytripapp.api.security;

import com.ytripapp.api.exception.AuthenticationFailureException;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    private MappingJackson2HttpMessageConverter messageConverter;
    private MessageSource messageSource;

    public AuthenticationFailureHandler(MappingJackson2HttpMessageConverter messageConverter, MessageSource messageSource) {
        this.messageConverter = messageConverter;
        this.messageSource = messageSource;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        messageConverter.write(
            new AuthenticationFailureException(
                messageSource.getMessage("failure.authentication", null, Locale.getDefault())
            ),
            MediaType.APPLICATION_JSON_UTF8,
            new ServletServerHttpResponse(response)
        );
    }
}
