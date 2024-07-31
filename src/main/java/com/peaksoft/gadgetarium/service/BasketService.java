package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMassage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.dto.response.BasketSummaryResponse;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Basket;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.BasketRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.UserRepository;
import jakarta.transaction.Transactional;
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
public class BasketService {

    BasketRepository basketRepository;
    ProductRepository productRepository;
    UserRepository userRepository;


    @Transactional
    public ResponseEntity<String> addProductToBasket(Long productId, Principal principal) {

        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.USER_NOT_FOUND));

        Basket basket = basketRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.BASKET_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID));

        basket.getProducts().add(product);
        basketRepository.save(basket);

        return ResponseEntity.ok("Product added to basket successfully");
    }

    @Transactional
    public BasketSummaryResponse getProductsFromBasket(Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.BASKET_NOT_FOUND));

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
    public ResponseEntity<String> deleteProductFromBasket(Long productId, Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.BASKET_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID));

        if (!basket.getProducts().contains(product)) {
            return new ResponseEntity<>("Product not found in the basket", HttpStatus.NOT_FOUND);
        }

        basket.getProducts().remove(product);
        basketRepository.save(basket);

        return new ResponseEntity<>("Product removed from the basket successfully", HttpStatus.OK);
    }

    @Transactional
    public ProductResponse getProductById(Long productId, Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.BASKET_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID));

        if (!basket.getProducts().contains(product)) {
            throw new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_THE_BASKET);
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
                .subCategory(product.getSubCategory())
                .createDate(product.getCreateDate())
                .memory(product.getMemory())
                .color(product.getColor())
                .brand(product.getBrand())
                .operationSystemNum(product.getOperationSystemNum())
                .dateOfRelease(product.getDateOfRelease())
                .processor(product.getProcessor())
                .guarantee(product.getGuarantee())
                .screen(product.getScreen())
                .simCard(product.getSimCard())
                .rating(product.getRating())
                .discount(product.getDiscount())
                .weight(product.getWeight())
                .price(product.getPrice())
                .build();
    }
}
