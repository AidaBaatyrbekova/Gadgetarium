package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.UserRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserResponse;
import com.peaksoft.gadgetarium.model.dto.request.UserSignInRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserLoginResponse;
import com.peaksoft.gadgetarium.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/sign-up-with-google")
    public Map<String, Object> registerWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return userService.saveWithGoogle(oAuth2AuthenticationToken);
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserSignInRequest request) {
        return userService.login(request);
    }

    @PutMapping("/updatePassword")
    public void updatePassword(@RequestBody UserUpdatePasswordRequest request) {
        userService.updatePassword(request);
    }
}
