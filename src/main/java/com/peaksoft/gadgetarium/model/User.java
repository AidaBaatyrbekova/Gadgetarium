package com.peaksoft.gadgetarium.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
Long id;
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
