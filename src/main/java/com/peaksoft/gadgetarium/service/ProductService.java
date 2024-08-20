package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.model.enums.Color;
import com.peaksoft.gadgetarium.model.enums.Memory;
import com.peaksoft.gadgetarium.model.enums.OperationMemory;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.SubCategoryRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductMapper productMapper;
    ProductRepository productRepository;
    SubCategoryRepository subCategoryRepository;
    BrandRepository brandRepository;
    MainPageService mainPageService;

    public ProductResponse createProduct(ProductRequest request) {
        return productMapper.mapToResponse(productRepository.save(productMapper.productMapper(request)));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND + id));
        return productMapper.mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found by id: {}", id);
                    return new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND + id);
                });
        productMapper.updateProductFromRequest(request, product);

        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.SUB_CATEGORY_NOT_FOUND + request.getSubCategoryId()));
            product.setSubCategory(subCategory);
        }
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.BRAND_NOT_FOUND_WITH_ID + request.getBrandId()));
            product.setBrand(brand);
        }
        return productMapper.mapToResponse(productRepository.save(product));
    }

    public String deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND + id));
        productRepository.delete(product);
        return "Successfully deleted product by id " + id;
    }

    public List<Product> filterProducts(String nameOfSubCategory, String brandName, Color color,
                                        Memory memory, OperationMemory operationMemory,
                                        Integer priceMin, Integer priceMax) {
        return productRepository.filterProducts(nameOfSubCategory, brandName, color, memory,
                operationMemory, priceMin, priceMax);
    }
}