package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductCompareMapper {
    public ProductCompareResponse toProductCompareResponse(Product product) {
        return ProductCompareResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .brandId(product.getBrandOfProduct().getId())
                .screen(product.getScreen())
                .processor(product.getProcessor())
                .weight(product.getWeight())
                .price(product.getPrice())
                .build();
    }
}
