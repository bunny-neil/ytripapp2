package com.ytripapp.domain;

import lombok.Data;

@Data
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
