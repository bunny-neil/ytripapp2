package com.ytripapp.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Embeddable
public class UserProfile {

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
