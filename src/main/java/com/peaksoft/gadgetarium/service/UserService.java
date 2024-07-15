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
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    AuthMapper authMapper;

    public UserResponse createUser(UserRequest request) {
        User user = authMapper.mapToUser(request);
        userRepository.save(user);
        log.info("Successfully created User " + user.getId());
        return authMapper.mapToResponse(user);
    }
}


