package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.dto.request.UserRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserResponse;
import com.peaksoft.gadgetarium.model.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User mapToUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        if(!request.getPassword().equals(request.getConfirmThePassword())){
            throw new RuntimeException("Password does not match");
        }
        user.setConfirmThePassword(request.getConfirmThePassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        return user;
    }

    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
