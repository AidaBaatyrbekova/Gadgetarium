package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.UserRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserResponse;
import com.peaksoft.gadgetarium.model.dto.request.UserSignInRequest;
import com.peaksoft.gadgetarium.model.dto.request.UserUpdatePasswordRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserLoginResponse;
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
    public UserLoginResponse login(@RequestBody UserSignInRequest request) {
        return userService.login(request);
    }

    @PutMapping("/updatePassword")
    public void updatePassword(@RequestBody UserUpdatePasswordRequest request) {
        userService.updatePassword(request);
    }
}
