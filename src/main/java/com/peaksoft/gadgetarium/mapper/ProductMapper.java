package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.SubCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductMapper {

    BrandRepository brandRepository;
    SubCategoryRepository subCategoryRepository;

    public Product productMapper(ProductRequest request) {
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setProductStatus(request.getProductStatus());
        product.setOperationMemory(request.getOperationMemory());
        product.setOperationSystem(request.getOperationSystem());
        product.setOperationSystemNum(request.getOperationSystemNum());
        product.setDateOfRelease(request.getDateOfRelease());
        product.setProcessor(request.getProcessor());
        product.setGuarantee(request.getGuarantee());
        product.setSimCard(request.getSimCard());
        product.setScreen(request.getScreen());
        product.setMemory(request.getMemory());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setPrice(request.getPrice());
        product.setRating(request.getRating());
        product.setDiscount(request.getDiscount());
        product.setCreateDate(LocalDate.now());

        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.SUB_CATEGORY_NOT_FOUND_WITH_ID + request.getSubCategoryId()));
            product.setSubCategory(subCategory);
        }
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.BRAND_NOT_FOUND_WITH_ID + request.getBrandId()));
            product.setBrand(brand);
        }
        return product;
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productStatus(product.getProductStatus())
                .brand(product.getBrand())
                .memory(product.getMemory())
                .color(product.getColor())
                .operationMemory(product.getOperationMemory())
                .screen(product.getScreen())
                .operationSystem(product.getOperationSystem())
                .operationSystemNum(product.getOperationSystemNum())
                .dateOfRelease(product.getDateOfRelease())
                .simCard(product.getSimCard())
                .processor(product.getProcessor())
                .weight(product.getWeight())
                .guarantee(product.getGuarantee())
                .rating(product.getRating())
                .discount(product.getDiscount())
                .price(product.getPrice())
                .createDate(product.getCreateDate())
                .subCategory(product.getSubCategory())
                .build();
    }

    public void updateProductFromRequest(ProductRequest request, Product product) {
        product.setProductName(request.getProductName());
        product.setProductStatus(request.getProductStatus());
        product.setOperationMemory(request.getOperationMemory());
        product.setOperationSystem(request.getOperationSystem());
        product.setOperationSystemNum(request.getOperationSystemNum());
        product.setDateOfRelease(request.getDateOfRelease());
        product.setProcessor(request.getProcessor());
        product.setGuarantee(request.getGuarantee());
        product.setSimCard(request.getSimCard());
        product.setScreen(request.getScreen());
        product.setMemory(request.getMemory());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setPrice(request.getPrice());
        product.setRating(request.getRating());
        product.setDiscount(request.getDiscount());
    }
}