package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.ProductCompareMapper;
import com.peaksoft.gadgetarium.model.dto.request.ProductCompareRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.ProductCompareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCompareService {

    ProductCompareRepository productCompareRepository;
    ProductCompareMapper productCompareMapper;

    public List<ProductCompareResponse> compareProducts(Long categoryId, boolean showDifferencesOnly) {
        List<Product> products = productCompareRepository.findByCategoryId(categoryId);

        if (showDifferencesOnly) {
            products = filterDifferences(products);
        }

        return products.stream()
                .map(productCompareMapper::toProductCompareResponse)
                .collect(Collectors.toList());
    }

    private List<Product> filterDifferences(List<Product> products) {
        if (products.isEmpty()) {
            return products;
        }

        Product referenceProduct = products.get(0);

        return products.stream()
                .filter(product -> !areProductsEqual(product, referenceProduct))
                .collect(Collectors.toList());
    }

    private boolean areProductsEqual(Product p1, Product p2) {
        return (p1.getBrandOfProduct() != null && p1.getBrandOfProduct().equals(p2.getBrandOfProduct())) &&
                (p1.getScreen() != null && p1.getScreen().equals(p2.getScreen())) &&
                (p1.getColor() != null && p1.getColor().equals(p2.getColor())) &&
                (p1.getOperationSystem() != null && p1.getOperationSystem().equals(p2.getOperationSystem())) &&
                (p1.getMemory() != null && p1.getMemory().equals(p2.getMemory())) &&
                (p1.getWeight() == p2.getWeight()) &&
                (p1.getSimCard() != null && p1.getSimCard().equals(p2.getSimCard()));
    }

}