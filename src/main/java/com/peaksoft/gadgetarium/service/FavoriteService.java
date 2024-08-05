package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMassage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.dto.response.FavoriteResponse;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

    public FavoriteResponse addFavorite(Long productId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.USER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID));
        user.getFavorites().add(product);
        userRepository.save(user);
        ProductResponse productResponse = productMapper.mapToResponse(product);
        return FavoriteResponse.builder()
                .userId(user.getId())
                .productId(product.getId())
                .productResponse(productResponse)
                .build();
    }

    //очищает все избранные товары текущего пользователя по его email.
    public void clearFavorites(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.USER_NOT_FOUND));
        user.getFavorites().clear();
        userRepository.save(user);
    }

    public List<FavoriteResponse> getFavorites(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.USER_NOT_FOUND));
        return user.getFavorites().stream()
                .map(product -> FavoriteResponse.builder()
                        .userId(user.getId())
                        .productId(product.getId())
                        .productResponse(productMapper.mapToResponse(product))
                        .build())
                .collect(Collectors.toList());
    }

    // удаляет один избранный товар по productId и email текущего пользователя.
    public void removeFavorite(Long productId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.USER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID));
        user.getFavorites().remove(product);
        userRepository.save(user);
    }
}
