package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.AuthMapper;
import com.peaksoft.gadgetarium.model.dto.response.UserRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserResponse;
import com.peaksoft.gadgetarium.model.dto.request.UserSignInRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserLoginResponse;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.UserRepository;
import com.peaksoft.gadgetarium.security.jwt.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    JwtUtil jwtUtil;
    AuthenticationManager authenticationManager;
    AuthMapper authMapper;

    public UserLoginResponse login(UserSignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неверный email или пароль!");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Неверный email или пароль!"));
        String jwt = jwtUtil.generateAccessToken(user);
        log.info("Successfully logged in! ");
        return UserLoginResponse.builder()
                .userName(user.getUsername())
                .role(user.getRole())
                .token(jwt)
                .build();

    }

    public UserResponse createUser(UserRequest request) {
        User user = authMapper.mapToUser(request);
        userRepository.save(user);
        log.info("Successfully created User " + user.getId());
        return authMapper.mapToResponse(user);
    }
}


