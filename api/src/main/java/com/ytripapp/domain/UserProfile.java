package com.ytripapp.domain;

import lombok.Data;

import javax.persistence.Embeddable;

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
    Gender gender;
    String phoneNo;
    String occupation;
    String introduction;
    String portraitUri;
}
