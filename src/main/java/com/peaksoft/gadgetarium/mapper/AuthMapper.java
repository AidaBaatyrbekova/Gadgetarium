package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.dto.UserRequest;
import com.peaksoft.gadgetarium.model.dto.UserResponse;
import com.peaksoft.gadgetarium.model.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User mapToUser(UserRequest request) {
        User user = new User();
        if (request.getName().length() < 2) {
            throw new RuntimeException("The name must be more than 5 symbols long");
        }
        user.setName(request.getName());
        user.setLastName(request.getLastname());
        user.setEmail(request.getEmail());
        if(!request.getPassword().equals(request.getConfirm_the_password())){
            throw new RuntimeException("Password does not match");
        }
        user.setConfirm_the_password(request.getConfirm_the_password());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        if (request.getPassword().length() < 4) {
            throw new RuntimeException("Password must contain more than 5 characters");
        }
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
