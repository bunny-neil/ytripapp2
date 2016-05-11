package com.ytripapp.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@Embeddable
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 2602763241146186570L;

    public enum Gender {
        Unspecified,
        Male,
        Female
    }

    String nickname;
    String firstName;
    String lastName;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String phoneNo;
    String occupation;
    @Column(columnDefinition = "longtext")
    String introduction;
    String portraitUri;
}
