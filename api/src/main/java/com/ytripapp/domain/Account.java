package com.ytripapp.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public abstract class Account {

    public enum Group {
        Guest,
        Host,
        Admin,
        Editor
    }

    private Long id;
    private Long version;
    private Group group;
    private boolean enabled;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateLastSignin;
    private String password;
    private String stipeCustId;
    private String apnsDeviceToken;
    private UserProfile profile;
    private List<AccountConnection> connections = new ArrayList<>();
    private Set<Authority> authorities = new HashSet<>();
}
