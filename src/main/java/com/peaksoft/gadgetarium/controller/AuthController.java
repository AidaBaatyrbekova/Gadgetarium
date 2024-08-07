package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.*;
import com.peaksoft.gadgetarium.model.dto.response.LoginResponse;
import com.peaksoft.gadgetarium.model.dto.response.UserResponse;
import com.peaksoft.gadgetarium.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "User Registration")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;

    @Operation(summary = "The User can register")
    @PostMapping("/sign-up")
    public UserResponse createUser(@RequestBody @Valid UserRequest request) {
        return userService.createUser(request);
    }

    @Operation(summary = "User register with google")
    @GetMapping("/sign-up-with-google")
    public Map<String, Object> registerWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return userService.saveWithGoogle(oAuth2AuthenticationToken);
    }

    @Operation(summary = "User's Login")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @Operation(summary = "The User reset password")
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        return userService.resetPassword(request);
    }
@Operation(summary = "The User can reset a password Token")
    @PostMapping("/resetPasswordToken")
    public ResponseEntity<String> resetPasswordToken(@RequestBody PasswordResetTokenRequest request) {
        return userService.resetPasswordToken(request);
    }

    @Operation(summary = "User update password")
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request) {
        return userService.updatePassword(request);
    }
}
