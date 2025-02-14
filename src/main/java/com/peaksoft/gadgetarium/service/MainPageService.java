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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainPageService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    public List<ProductResponse> getMainPage() {
        List<ProductResponse> resultResponse = new ArrayList<>();
        resultResponse.addAll(findDiscountedProducts());
        resultResponse.addAll(findNewDevices());
        resultResponse.addAll(findRecommendedProducts());
        return resultResponse;
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
}
