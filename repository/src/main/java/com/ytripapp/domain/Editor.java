package com.ytripapp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Editor")
public class Editor extends Account {

    private static final long serialVersionUID = 4552242554355989415L;

    public Editor() {
        this.setGroup(Group.Editor);
    }
}
