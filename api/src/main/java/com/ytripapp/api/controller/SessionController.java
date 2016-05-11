package com.ytripapp.api.controller;

import com.ytripapp.api.security.AuthenticationRequiredException;
import com.ytripapp.api.security.Passport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @RequestMapping
    public ResponseEntity<Passport> heartBeat() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken
            || authentication instanceof RememberMeAuthenticationToken) {
            return new ResponseEntity<>((Passport)authentication.getPrincipal(), HttpStatus.OK);
        }
        throw new AuthenticationRequiredException();
    }
}
