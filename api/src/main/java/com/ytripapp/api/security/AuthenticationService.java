package com.ytripapp.api.security;

import com.ytripapp.domain.AccountConnection;
import com.ytripapp.domain.AccountConnection.ConnectionType;
import com.ytripapp.repository.AccountConnectionRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AuthenticationService implements UserDetailsService {

    private AccountConnectionRepository connectionRepository;

    public AuthenticationService(AccountConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountConnection> found = connectionRepository.findByConnectionIdAndConnectionType(username, ConnectionType.Email);
        AccountConnection connection = found.orElseThrow(() -> new UsernameNotFoundException(""));
        return new Passport(connection);
    }
}
