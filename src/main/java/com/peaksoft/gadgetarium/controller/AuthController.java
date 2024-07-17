package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.*;
import com.peaksoft.gadgetarium.model.dto.response.LoginResponse;
import com.peaksoft.gadgetarium.model.dto.response.UserResponse;
import com.peaksoft.gadgetarium.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;

    @PostMapping("/sign-up")
    public UserResponse createUser(@RequestBody @Valid UserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

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
