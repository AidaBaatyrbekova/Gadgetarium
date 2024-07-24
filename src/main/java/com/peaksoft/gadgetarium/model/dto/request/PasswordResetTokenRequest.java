package com.peaksoft.gadgetarium.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetTokenRequest {
    String email;
    String token;
    String newPassword;
    String confirmNewPassword;
}