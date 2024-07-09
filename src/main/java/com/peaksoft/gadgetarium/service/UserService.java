package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.AuthMapper;
import com.peaksoft.gadgetarium.model.dto.UserRequest;
import com.peaksoft.gadgetarium.model.dto.UserResponse;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    @Autowired
    UserRepository userRepository;
    AuthMapper authMapper;

    public UserResponse createUser(UserRequest request) {
        User user = authMapper.mapToUser(request);
        if (userRepository.findAll().isEmpty()) {
            for (User user1 : userRepository.findAll()) {
                if (user1.getEmail().equals(request.getEmail())) {
                    if (user1.getPassword().equals(request.getPassword())) {
                        throw new RuntimeException("Такой email уже существует");
                    }
                }
            }
            userRepository.save(user);
            log.info("Successfully created User " + user.getId());
        }
        return authMapper.mapToResponse(user);
    }

}
