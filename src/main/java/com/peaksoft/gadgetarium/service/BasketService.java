package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.dto.request.BasketRequest;
import com.peaksoft.gadgetarium.model.entities.Basket;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.BasketRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BasketService {

    BasketRepository basketRepository;
    ProductRepository productRepository;

    @Transactional
    public ResponseEntity<String> addProductToBasket(BasketRequest request) {
        Basket basket = basketRepository.findById(request.getBasketId())
                .orElseThrow(() -> new RuntimeException("Basket not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        basket.getProducts().add(product);
        basketRepository.save(basket);

        return ResponseEntity.ok("Product added to basket successfully");
    }
}
