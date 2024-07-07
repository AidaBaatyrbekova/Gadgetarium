package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.dto.request.UserSignInRequest;
import com.peaksoft.gadgetarium.model.dto.request.UserUpdatePasswordRequest;
import com.peaksoft.gadgetarium.model.dto.response.UserLoginResponse;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.UserRepository;
import com.peaksoft.gadgetarium.security.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public Map<String, Object> saveWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken)  {
        OAuth2AuthenticatedPrincipal principal = oAuth2AuthenticationToken.getPrincipal();
        if (oAuth2AuthenticationToken == null) {
            throw new IllegalArgumentException("The token must not be null");
        }
        Map<String, Object> attributes = principal.getAttributes();
        User user = new User();
        user.setName((String) attributes.get("given_name"));
        user.setLastName((String) attributes.get("family_name"));
        user.setEmail((String) attributes.get("email"));
        user.setPassword((String) attributes.get("given_name"));
        user.setCreateDate(LocalDate.now());
        user.setRole(Role.USER);
        userRepository.save(user);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("name", user.getName());
        response.put("lastName", user.getLastName());
        response.put("email", user.getEmail());
        response.put("creatDate", user.getCreateDate());
        return response;
    }
}


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс для управления операциями пользователей, такими как аутентификация и авторизация.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    JwtUtil jwtUtil;
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;

    public UserLoginResponse login(UserSignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неверный email или пароль!");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Неверный email или пароль!"));
        String jwt = jwtUtil.generateToken(user);
        return UserLoginResponse.builder()
                .userName(user.getUsername())
                .role(user.getRole())
                .token(jwt)
                .build();
    }

    public boolean isPasswordSecure(String newPassword) {
        if (newPassword.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        for (char ch : newPassword.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSpecialChar = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким email не найден"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неверный текущий пароль!");
        }

        if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
            throw new RuntimeException("Введите новый пароль!");
        }

        if (request.getNewConfirmPassword() == null || request.getNewConfirmPassword().isEmpty()) {
            throw new RuntimeException("Подтвердите новый пароль!");
        }

        if (!request.getNewPassword().equals(request.getNewConfirmPassword())) {
            throw new RuntimeException("Новые пароли не совпадают!");
        }

        if (!isPasswordSecure(request.getNewPassword())) {
            throw new RuntimeException("Новый пароль ненадежен! Пароль должен содержать как минимум 8 символов, включая заглавные и строчные буквы, цифры и специальные символы.");
        }

        String hashedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
