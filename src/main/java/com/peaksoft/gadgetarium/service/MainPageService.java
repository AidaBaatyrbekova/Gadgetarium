package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.enums.ProductStatus;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainPageService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    public MainPage getMainPage() {
        List<ProductResponse> discountedProducts = findDiscountedProducts();
        List<ProductResponse> newArrivals = findNewDevices();
        List<ProductResponse> recommendedProducts = findRecommendedProducts();
        return new MainPage(discountedProducts, newArrivals, recommendedProducts);
    }

    public List<ProductResponse> findDiscountedProducts() {
        List<Product> products = productRepository.findDiscounted();
        return products.stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findNewDevices() {
        List<Product> products = productRepository.findByProductStatus(ProductStatus.NEW_DEVICES);
        return products.stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findRecommendedProducts() {
        List<Product> products = productRepository.findRecommended();
        return products.stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public static class MainPage {
        List<ProductResponse> discountedProducts;
        List<ProductResponse> newArrivals;
        List<ProductResponse> recommendedProducts;

        public MainPage(List<ProductResponse> discountedProducts, List<ProductResponse> newArrivals,
                        List<ProductResponse> recommendedProducts) {
            this.discountedProducts = discountedProducts;
            this.newArrivals = newArrivals;
            this.recommendedProducts = recommendedProducts;
        }
    }
}
