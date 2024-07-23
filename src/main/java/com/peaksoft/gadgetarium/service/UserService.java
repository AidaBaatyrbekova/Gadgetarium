package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.mapper.AuthMapper;
import com.peaksoft.gadgetarium.model.dto.request.*;
import com.peaksoft.gadgetarium.model.dto.response.LoginResponse;
import com.peaksoft.gadgetarium.model.dto.response.UserResponse;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.model.enums.Role;
import com.peaksoft.gadgetarium.repository.UserRepository;
import com.peaksoft.gadgetarium.security.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    MailService mailService;
    AuthenticationManager authenticate;
    AuthMapper authMapper;
    JwtUtil jwtUtil;

    public UserResponse createUser(UserRequest request) {
        User user = authMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setConfirmThePassword(passwordEncoder.encode(request.getConfirmThePassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("Successfully created User " + user.getId());
        return authMapper.mapToResponse(user);
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
    public ResponseEntity<String> resetPassword(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким email не найден"));

        String token = UUID.randomUUID().toString().substring(0, 6);
        user.setResetPasswordToken(token);
        userRepository.save(user);

        sendResetPasswordEmail(user.getEmail(), token);

        return new ResponseEntity<>("Вам отправлено сообщение на вашу почту!", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> resetPasswordToken(PasswordResetTokenRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким email не найден"));

        if (!user.getResetPasswordToken().equals(request.getToken())) {
            throw new IllegalArgumentException("Неверный токен сброса пароля!");
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new IllegalArgumentException("Новые пароли не совпадают!");
        }

        if (!isPasswordSecure(request.getNewPassword())) {
            throw new IllegalArgumentException("Новый пароль ненадежен! Пароль должен содержать как минимум 8 символов, включая заглавные и строчные буквы, цифры и специальные символы.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetPasswordToken(null);
        userRepository.save(user);

        return new ResponseEntity<>("Пароль успешно обновлен!", HttpStatus.OK);
    }

    private void sendResetPasswordEmail(String email, String token) {
        String subject = "Password Reset Request";
        String message = "Your password reset code is: " + token;
        mailService.sendSimpleMessage(email, subject, message);
    }

    @Transactional
    public ResponseEntity<String> updatePassword(UpdatePasswordRequest request) {
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

        return new ResponseEntity<>("Пароль успешно обновлен!", HttpStatus.OK);
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неверный email или пароль!");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Неверный email или пароль!"));
        String jwt = jwtUtil.generateAccessToken(user);
        log.info("Successfully logged in! ");
        return LoginResponse.builder()
                .userName(user.getUsername())
                .role(user.getRole())
                .token(jwt)
                .build();

    }

    public Map<String, Object> saveWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        OAuth2AuthenticatedPrincipal principal = oAuth2AuthenticationToken.getPrincipal();
        if (oAuth2AuthenticationToken == null) {
            throw new NotFoundException("The token must not be null");
        }
        Map<String, Object> attributes = principal.getAttributes();
        User user = new User();
        user.setName((String) attributes.get("given_name"));
        user.setLastName((String) attributes.get("family_name"));
        user.setEmail((String) attributes.get("email"));
        user.setPassword(passwordEncoder.encode((String) attributes.get("given_name")));
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