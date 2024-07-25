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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Transactional
    public List<Product> getProductsFromBasket(BasketRequest request) {
        Basket basket = basketRepository.findById(request.getBasketId())
                .orElseThrow(() -> new RuntimeException("Basket not found"));

        return basket.getProducts();
    }

    @Transactional
    public ResponseEntity<String> deleteProductFromBasket(BasketRequest request) {
        Basket basket = basketRepository.findById(request.getBasketId())
                .orElseThrow(() -> new RuntimeException("Basket not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!basket.getProducts().contains(product)) {
            return new ResponseEntity<>("Product not found in the basket", HttpStatus.NOT_FOUND);
        }

        basket.getProducts().remove(product);
        basketRepository.save(basket);

        return new ResponseEntity<>("Product removed from the basket successfully", HttpStatus.OK);
    }
}
