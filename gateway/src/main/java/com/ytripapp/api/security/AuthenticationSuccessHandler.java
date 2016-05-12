package com.ytripapp.api.security;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    MappingJackson2HttpMessageConverter messageConverter;

    public AuthenticationSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Passport passport = (Passport)authentication.getPrincipal();
        messageConverter.write(passport, MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
        clearAuthenticationAttributes(request);
    }
}
