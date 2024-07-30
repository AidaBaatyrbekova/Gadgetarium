package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.exception.ExceptionMassage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.SubCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
        product.setCreateDate(LocalDate.now());
        product.setSimCard(request.getSimCard());
        product.setScreen(request.getScreen());
        product.setMemory(request.getMemory());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setPrice(request.getPrice());
        product.setRating(request.getRating());
        product.setDiscount(request.getDiscount());

        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                    .orElseThrow(() -> new NotFoundException(ExceptionMassage.SUB_CATEGORY_NOT_FOUND_WITH_ID + request.getSubCategoryId()));
            product.setSubCategory(subCategory);

        }
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new NotFoundException(ExceptionMassage.BRAND_NOT_FOUND_WITH_ID + request.getBrandId()));
            product.setBrandOfProduct(brand);
        }
        return product;
    }

    public ProductResponse mapToResponse(Product product) {
        SubCategory subCategory = product.getSubCategory();
        Category category = (subCategory != null) ? subCategory.getCategoryOfSubCategory() : null;
        Brand brand = product.getBrandOfProduct();
        Long brandId = (brand != null) ? brand.getId() : null;
        Long subCategoryId = (subCategory != null) ? subCategory.getId() : null;

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productStatus(product.getProductStatus())
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
                .rating(product.getRating())
                .discount(product.getDiscount())
                .price(product.getPrice())
                .createDate(product.getCreateDate())
                .subCategoryId(subCategoryId)
                .category(category)
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
