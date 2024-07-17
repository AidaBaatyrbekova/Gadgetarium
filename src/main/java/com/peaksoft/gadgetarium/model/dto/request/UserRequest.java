package com.peaksoft.gadgetarium.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String name;
    @NotBlank(message = "lastName is mandatory!")
    @Size(min = 2, max = 30, message = "LastName should be between 2 and 30 characters!")
    String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    String email;
    @NotBlank(message = "password is mandatory!")
    @Size(min = 6, max = 20, message = "password must be between 6 and 20 characters!")
    String password;
    String confirmThePassword;
    @NotBlank(message = "phoneNumber should not be empty!")
    @Size(min = 6, max = 30, message = "phoneNumber must be between 6 and 30 characters!")
    String phoneNumber;
}
