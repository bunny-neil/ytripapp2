package com.ytripapp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Guest")
public class Guest extends Account {

    private static final long serialVersionUID = 6160886345890923809L;

    public Guest() {
        this.setGroup(Group.Guest);
    }
}
