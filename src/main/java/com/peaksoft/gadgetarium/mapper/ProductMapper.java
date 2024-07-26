package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.SubCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductMapper {

    BrandRepository brandRepository;
    SubCategoryRepository subCategoryRepository;
    SubCategoryRepository categoryRepository;

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
        product.setCreateDate(request.getCreateDate());
        product.setSimCard(request.getSimCard());
        product.setScreen(request.getScreen());
        product.setMemory(request.getMemory());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setPrice(request.getPrice());
        product.setRating(request.getRating());
        product.setDiscount(request.getDiscount());

        if (request.getCategoryId() != null) {

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id " + request.getCategoryId())).getCategoryOfSubCategory();
            product.setCategory(category);
        }

        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("SubCategory not found with id " + request.getSubCategoryId()));
            product.setSubCategory(subCategory);
        }

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found with id " + request.getBrandId()));
            product.setBrandOfProduct(brand);
        }
        return product;
    }

    public ProductResponse mapToResponse(Product product) {
        Long categoryId = null;
        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
        }

        Long subCategoryId = null;
        if (product.getSubCategory() != null) {
            subCategoryId = product.getSubCategory().getId();
        }

        Long brandId = null;
        if (product.getBrandOfProduct() != null) {
            brandId = product.getBrandOfProduct().getId();
        }

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productStatus(product.getProductStatus())
                .subCategoryId(subCategoryId)
                .categoryId(categoryId)
                .brandId(brandId)
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
                .rating(String.valueOf(product.getRating()))
                .discount(product.getDiscount())
                .price(product.getPrice())
                .createDate(product.getCreateDate())
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
        product.setCreateDate(request.getCreateDate());
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