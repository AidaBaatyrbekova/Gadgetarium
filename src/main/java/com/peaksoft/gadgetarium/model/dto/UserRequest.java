package com.peaksoft.gadgetarium.model.dto;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    String name;

    @Column(name = "last-Name")
    String lastname;

    @Column(unique = true)
    String email;

    String password;

     String confirm_the_password;

    private String phoneNumber;


}
