package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.UserRequest;
import com.peaksoft.gadgetarium.model.dto.UserResponse;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/auth")
public class AuthController {

    UserService userService;

    @PostMapping("/sign-up")
    public UserResponse createUser(@RequestBody @Valid UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/sign-in-with-google")
    public Map<String, Object> registerWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return userService.saveWithGoogle(oAuth2AuthenticationToken);
    }
}
