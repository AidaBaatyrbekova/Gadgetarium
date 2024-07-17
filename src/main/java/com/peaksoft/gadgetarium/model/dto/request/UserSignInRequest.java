package com.peaksoft.gadgetarium.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignInRequest {

    String email;
    String password;
}