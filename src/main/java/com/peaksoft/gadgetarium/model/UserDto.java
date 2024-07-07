package com.peaksoft.gadgetarium.model;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto{
    String name;

    @Column(name = "last-Name")
    String lastname;

    @Column(unique = true)
    String email;

    String password;

    @Column(name = "phone_number")
    String phoneNumber;
    String Confirm_the_password;
    String gender;
    String local;

}
