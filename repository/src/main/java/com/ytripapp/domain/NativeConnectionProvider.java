package com.ytripapp.domain;

import com.ytripapp.domain.AccountConnection.ConnectionType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NativeConnectionProvider implements ConnectionProvider {

    private String nickname;
    private String email;
    private String password;

    @Override
    public AccountConnection connection() {
        AccountConnection connection = new AccountConnection();
        connection.setConnectionId(email);
        connection.setConnectionType(ConnectionType.Email);
        return connection;
    }

    @Override
    public UserProfile profile() {
        UserProfile profile = new UserProfile();
        profile.setNickname(nickname);
        return profile;
    }
}