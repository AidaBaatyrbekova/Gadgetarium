package com.peaksoft.gadgetarium.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO для запроса на вход пользователя, содержащего учетные данные для входа.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignInRequest {

    String email;
    String password;
}