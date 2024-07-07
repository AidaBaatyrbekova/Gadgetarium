package com.peaksoft.gadgetarium.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO для запроса на обновление пароля пользователя.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdatePasswordRequest {

    String email;
    String password;
    String newPassword;
    String newConfirmPassword;
}