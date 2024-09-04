package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.InvalidPasswordException;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.mapper.AuthMapper;
import com.peaksoft.gadgetarium.model.dto.request.*;
import com.peaksoft.gadgetarium.model.dto.response.LoginResponse;
import com.peaksoft.gadgetarium.model.dto.response.UserResponse;
import com.peaksoft.gadgetarium.model.entities.Basket;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.model.enums.Role;
import com.peaksoft.gadgetarium.repository.BasketRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
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

    ProductRepository productRepository;
    UserRepository userRepository;
    BasketRepository basketRepository;
    PasswordEncoder passwordEncoder;
    MailService mailService;
    AuthenticationManager authenticate;
    AuthMapper authMapper;
    JwtUtil jwtUtil;

    public UserResponse createUser(UserRequest request) {
        User user = authMapper.mapToUser(request);

        if (!isPasswordSecure(request.getPassword())) {
            throw new InvalidPasswordException(ExceptionMessage.PASSWORD_NOT_SECURE);
        }
        if (!request.getPassword().equals(request.getConfirmThePassword())) {
            throw new InvalidPasswordException(ExceptionMessage.PASSWORD_MISMATCH);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        Basket basket = new Basket();
        basket.setUser(user);
        basketRepository.save(basket);

        user.setBasket(basket);

        log.info("Successfully created User " + user.getId());
        return authMapper.mapToResponse(user);
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(ExceptionMessage.AUTHORIZATION_ERROR);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.AUTHORIZATION_ERROR));
        String jwt = jwtUtil.generateAccessToken(user);
        log.info("Successfully logged in! ");
        return LoginResponse.builder()
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
    public ResponseEntity<String> resetPassword(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.USER_NOT_FOUND_BY_EMAIL));

        String token = UUID.randomUUID().toString().substring(0, 6);
        user.setResetPasswordToken(token);
        userRepository.save(user);

        sendResetPasswordEmail(user.getEmail(), token);

        return new ResponseEntity<>("A message has been sent to your email to reset your password!", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> resetPasswordToken(PasswordResetTokenRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.USER_NOT_FOUND_BY_EMAIL));

        if (!user.getResetPasswordToken().equals(request.getToken())) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_RESET_TOKEN);
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new IllegalArgumentException(ExceptionMessage.PASSWORDS_DONT_MATCH);
        }

        if (!isPasswordSecure(request.getNewPassword())) {
            throw new IllegalArgumentException(ExceptionMessage.PASSWORD_NOT_SECURE);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetPasswordToken(null);
        userRepository.save(user);

        return new ResponseEntity<>("Password successfully updated!", HttpStatus.OK);
    }

    private void sendResetPasswordEmail(String email, String token) {
        String subject = "Password Reset Request";
        String message = "Your password reset code is: " + token;
        mailService.sendSimpleMessage(email, subject, message);
    }

    @Transactional
    public ResponseEntity<String> updatePassword(UpdatePasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.USER_NOT_FOUND_BY_EMAIL));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException(ExceptionMessage.CURRENT_PASSWORD_INCORRECT);
        }

        if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
            throw new InvalidPasswordException(ExceptionMessage.ENTER_NEW_PASSWORD);
        }

        if (request.getNewConfirmPassword() == null || request.getNewConfirmPassword().isEmpty()) {
            throw new InvalidPasswordException(ExceptionMessage.CONFIRM_NEW_PASSWORD);
        }

        if (!request.getNewPassword().equals(request.getNewConfirmPassword())) {
            throw new InvalidPasswordException(ExceptionMessage.PASSWORDS_DONT_MATCH);
        }

        if (!isPasswordSecure(request.getNewPassword())) {
            throw new InvalidPasswordException(ExceptionMessage.PASSWORD_NOT_SECURE);
        }

        String hashedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        return new ResponseEntity<>("Password successfully updated!", HttpStatus.OK);
    }

    public Map<String, Object> saveWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        OAuth2AuthenticatedPrincipal principal = oAuth2AuthenticationToken.getPrincipal();
        if (oAuth2AuthenticationToken == null) {
            throw new NotFoundException(ExceptionMessage.TOKEN_MUST_NOT_BE_NULL);
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
