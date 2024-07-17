package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.CategoryRepository;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductMapper {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    public Product productMapper(ProductRequest request) {
        Product product = new Product();
        product.setOperationMemory(request.getOperationMemory());
        product.setOperationSystemNum(request.getOperationSystemNum());
        product.setOperationSystem(request.getOperationSystem());
        product.setDateOfRelease(request.getDateOfRelease());
        product.setProductStatus(request.getProductStatus());
        product.setProductName(request.getProductName());
        product.setProcessor(request.getProcessor());
        product.setGuarantee(request.getGuarantee());
        product.setSimCard(request.getSimCard());
        product.setCreateDate(request.getCreateDate());
        product.setMemory(request.getMemory());
        product.setScreen(request.getScreen());
        product.setWeight(request.getWeight());
        product.setColor(request.getColor());
        product.setPrice(request.getPrice());

        Optional<Category> category = Optional.empty();
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId());
        }
        category.ifPresent(product::setCategory);

        Optional<Brand> brand = Optional.empty();
        if (request.getBrandId() != null) {
            brand = brandRepository.findById(request.getBrandId());
        }
        brand.ifPresent(product::setBrandOfProduct);
        return product;
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productStatus(product.getProductStatus())
                .categoryId(product.getId())
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
}
