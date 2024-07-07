package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.User;
import com.peaksoft.gadgetarium.model.dto.UserRequest;
import com.peaksoft.gadgetarium.model.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    public User mapToUser(UserRequest request) {
        User user = new User();
        if (request.getName().length() < 2) {
            throw new RuntimeException("Иья студента должно состоить больше 2 символа");
        }
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setLocal(request.getLocal());
        user.setGender(request.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setConfirm_the_password(request.getConfirm_the_password());
        user.setPassword(request.getPassword());
        if (request.getPassword().length() < 4) {
            throw new RuntimeException("Пороль должен содержить больше 5 символа");
        }
        return user;
    }

    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .local(user.getLocal())
                .Confirm_the_password(user.getConfirm_the_password())
                .gender(user.getGender())
                .build();
    }
}
