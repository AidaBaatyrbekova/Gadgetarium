package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductCompareMapper {

    public ProductCompareResponse toProductCompareResponse(Product product) {
        return ProductCompareResponse.builder()
                .id(product.getId())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .brandId(product.getBrandOfProduct() != null ? product.getBrandOfProduct().getId() : null)
                .operationSystem(product.getOperationSystem() != null ? product.getOperationSystem().name() : null)
                .screen(product.getScreen())
                .color(product.getColor() != null ? product.getColor().name() : null)
                .memory(product.getMemory() != null ? product.getMemory().name() : null)
                .weight(product.getWeight())
                .simCard(product.getSimCard())
                .build();
    }
}