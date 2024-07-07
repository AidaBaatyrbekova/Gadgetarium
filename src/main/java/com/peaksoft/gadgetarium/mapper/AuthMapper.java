package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.User;
import com.peaksoft.gadgetarium.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    public User fromDto(UserDto userDto) {
        User user1 = new User();
        user1.setName(userDto.getName());
        user1.setEmail(userDto.getEmail());
        user1.setLastname(userDto.getLastname());
        user1.setPassword(userDto.getPassword());
        user1.setLocal(userDto.getLocal());
        user1.setGender(userDto.getGender());
        user1.setPhoneNumber(userDto.getPhoneNumber());
        user1.setConfirm_the_password(userDto.getConfirm_the_password());
        return user1;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setLastname(user.getLastname());
        userDto.setPassword(user.getPassword());
        userDto.setLocal(user.getLocal());
        userDto.setGender(user.getGender());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setConfirm_the_password(user.getConfirm_the_password());
        return userDto;
    }
}
