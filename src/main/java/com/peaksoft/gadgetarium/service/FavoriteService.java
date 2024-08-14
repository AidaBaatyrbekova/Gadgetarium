package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMassage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FavoriteService {

    UserRepository userRepository;
    ProductRepository productRepository;
    ProductMapper productMapper;

    public ProductResponse addFavorite(Long productId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> {
                    log.error("User not found with email:{}", principal.getName());
                    return new NotFoundException(ExceptionMassage.USER_NOT_FOUND);
                });
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with Id:{}", productId);
                    return new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID);
                });
        user.getFavorites().add(product);
        userRepository.save(user);
        return productMapper.mapToResponse(product);
    }

    //очищает все избранные товары текущего пользователя по его email.
    public ResponseEntity<String> clearFavorites(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new NotFoundException(ExceptionMassage.USER_NOT_FOUND);
                });
        user.getFavorites().clear();
        userRepository.save(user);
        log.info("All favorites successfully deleted for user with email: {}", email);
        return new ResponseEntity<>("All favorite successfully deleted", HttpStatus.OK);
    }

    public List<ProductResponse> getFavorites(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new NotFoundException(ExceptionMassage.USER_NOT_FOUND);
                });
        return user.getFavorites().stream()
                .map(productMapper::mapToResponse).collect(Collectors.toList());
    }

    // удаляет один избранный товар по productId и email текущего пользователя.
    public ResponseEntity<String> removeFavorite(Long productId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new NotFoundException(ExceptionMassage.USER_NOT_FOUND);
                });
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID);
                });
        if (!user.getFavorites().contains(product)) {
            log.error("Favorite not found with product ID: {} for user with email: {}", productId, email);
            throw new NotFoundException(ExceptionMassage.FAVORITE_NOT_FOUND);
        }
        user.getFavorites().remove(product);
        userRepository.save(user);
        log.info("Favorite successfully deleted for user with email: {}", email);
        return new ResponseEntity<>("Favorite successfully deleted", HttpStatus.OK);
    }
}
