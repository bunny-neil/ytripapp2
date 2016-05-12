package com.ytripapp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Host")
public class Host extends Account {

    private static final long serialVersionUID = -7733900132150123817L;

    public Host() {
        this.setGroup(Group.Host);
    }
}
