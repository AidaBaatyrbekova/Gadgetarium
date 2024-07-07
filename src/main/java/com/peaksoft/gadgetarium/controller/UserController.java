package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.UserSignInRequest;
import com.peaksoft.gadgetarium.model.dto.request.UserUpdatePasswordRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserLoginResponse;
import com.peaksoft.gadgetarium.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки запросов, связанных с пользователем.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserSignInRequest request) {
        return userService.login(request);
    }

    @PutMapping("/updatePassword")
    public void updatePassword(@RequestBody UserUpdatePasswordRequest request) {
        userService.updatePassword(request);
    }
}
