package com.ytripapp.api.security;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private MappingJackson2HttpMessageConverter messageConverter;

    public AuthenticationSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
        throws IOException, ServletException {
        messageConverter.write(
            authentication.getPrincipal(),
            MediaType.APPLICATION_JSON_UTF8,
            new ServletServerHttpResponse(response));
    }
}
