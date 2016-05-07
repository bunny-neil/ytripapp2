package com.ytripapp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends Account {

    private static final long serialVersionUID = 4230278488315631947L;

    public Admin() {
        this.setGroup(Group.Admin);
    }
}
