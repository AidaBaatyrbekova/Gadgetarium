package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.mapper.AuthMapper;
import com.peaksoft.gadgetarium.model.User;
import com.peaksoft.gadgetarium.model.UserDto;
import com.peaksoft.gadgetarium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;
    AuthMapper authMapper;

    @PostMapping("/sign-up")
    public UserDto registerUser(@RequestBody UserDto userDto) {
        User user = authMapper.fromDto(userDto);
        User registeredUser = userService.registerUser(user);
        return authMapper.toDto(registeredUser);
    }
}
