package com.ytripapp.api.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ytripapp.domain.AccountConnection;
import com.ytripapp.domain.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class Passport implements UserDetails {

    private static final long serialVersionUID = -1918215767801928666L;

    private String username;
    private String passoword;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;
    private UserProfile profile;

    public Passport(AccountConnection connection) {
        username = connection.getConnectionId();
        passoword = connection.getAccount().getPassword();
        enabled = connection.getAccount().isEnabled();
        authorities = connection.getAccount().getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.name()))
            .collect(Collectors.toSet());
        profile = connection.getAccount().getProfile();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return passoword;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserProfile getProfile() {
        return profile;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
