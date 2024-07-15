package com.peaksoft.gadgetarium.model.dto.response;

import com.peaksoft.gadgetarium.model.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginResponse {

    String userName;
    Role role;
    String token;
}