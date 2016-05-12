package com.ytripapp.domain;

public interface ConnectionProvider {

    enum ProviderName {
        Native,
        Wechat,
        Facebook
    }

    AccountConnection connection();

    UserProfile profile();
}
