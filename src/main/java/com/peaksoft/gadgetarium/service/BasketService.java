package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.dto.request.BasketRequest;
import com.peaksoft.gadgetarium.model.dto.response.BasketSummaryResponse;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
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
import java.util.stream.Collectors;

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
    public BasketSummaryResponse getProductsFromBasket(BasketRequest request) {
        Basket basket = basketRepository.findById(request.getBasketId())
                .orElseThrow(() -> new RuntimeException("Basket not found"));

        List<ProductResponse> productResponses = basket.getProducts().stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());

        int quantity = productResponses.size();
        int totalAmount = productResponses.stream()
                .mapToInt(ProductResponse::getPrice)
                .sum();
        int totalDiscount = productResponses.stream()
                .mapToInt(ProductResponse::getDiscount)
                .sum();
        int totalSum = totalAmount - totalDiscount;

        return BasketSummaryResponse.builder()
                .products(productResponses)
                .quantity(quantity)
                .price(totalAmount)
                .discount(totalDiscount)
                .totalSum(totalSum)
                .build();
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

    @Transactional
    public ProductResponse getProductById(BasketRequest request) {
        Basket basket = basketRepository.findById(request.getBasketId())
                .orElseThrow(() -> new RuntimeException("Basket not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!basket.getProducts().contains(product)) {
            throw new RuntimeException("Product not found in the basket");
        }

        return convertToProductResponse(product);
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productStatus(product.getProductStatus())
                .operationMemory(product.getOperationMemory())
                .operationSystem(product.getOperationSystem())
                .subCategoryId(product.getSubCategory().getId())
                .createDate(product.getCreateDate())
                .memory(product.getMemory())
                .color(product.getColor())
                .categoryId(product.getCategory().getId())
                .brandId(product.getBrandOfProduct().getId())
                .operationSystemNum(product.getOperationSystemNum())
                .dateOfRelease(product.getDateOfRelease())
                .processor(product.getProcessor())
                .guarantee(product.getGuarantee())
                .screen(product.getScreen())
                .simCard(product.getSimCard())
                .rating(String.valueOf(product.getRating()))
                .discount(product.getDiscount())
                .weight(product.getWeight())
                .price(product.getPrice())
                .build();
    }
}
