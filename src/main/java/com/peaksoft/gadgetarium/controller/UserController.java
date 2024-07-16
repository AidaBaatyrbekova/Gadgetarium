package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.PasswordResetRequest;
import com.peaksoft.gadgetarium.model.dto.request.PasswordResetTokenRequest;
import com.peaksoft.gadgetarium.model.dto.request.UpdatePasswordRequest;
import com.peaksoft.gadgetarium.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody PasswordResetRequest request) {
        userService.resetPassword(request);
    }

    @PostMapping("/resetPasswordToken")
    public void resetPasswordToken(@RequestBody PasswordResetTokenRequest request) {
        userService.resetPasswordToken(request);
    }

    @PutMapping("/updatePassword")
    public void updatePassword(@RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(request);
    }
}
