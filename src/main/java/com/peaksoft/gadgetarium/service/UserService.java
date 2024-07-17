package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.AuthMapper;
import com.peaksoft.gadgetarium.model.dto.UserRequest;
import com.peaksoft.gadgetarium.model.dto.UserResponse;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.model.enums.Role;
import com.peaksoft.gadgetarium.repository.UserRepository;
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


