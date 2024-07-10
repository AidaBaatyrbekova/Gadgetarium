package com.peaksoft.gadgetarium.model.dto;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long id;
    String name;

    @Column(name = "last-Name")
    String lastname;

    @Column(unique = true)
    String email;
     String phoneNumber;

}
