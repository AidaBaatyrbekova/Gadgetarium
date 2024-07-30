package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.SubCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductMapper productMapper;
    ProductRepository productRepository;
    SubCategoryRepository subCategoryRepository;
    BrandRepository brandRepository;
    SubCategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.productMapper(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.mapToResponse(savedProduct);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        return productMapper.mapToResponse(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found by id: {}", id);
                    return new RuntimeException("Not found product by id " + id);
                });
        productMapper.updateProductFromRequest(request, product);

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

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id " + request.getCategoryId())).getCategoryOfSubCategory();
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found Product by id " + id));
        productRepository.delete(product);
        return "Successfully deleted product by id " + id;
    }

    public List<ProductResponse> findDiscountedProducts() {
        log.info("Fetching discounted products");
        return productRepository.findDiscounted()
                .stream()
                .map(productMapper::mapToResponse)
                .toList();
    }
    public List<ProductResponse> findNewArrivals() {
        log.info("Fetching new arrival products");
        return productRepository.findNewArrivals()
                .stream()
                .map(productMapper::mapToResponse)
                .toList();
    }
    public List<ProductResponse> findRecommendedProducts() {
        log.info("Fetching recommended products");
        return productRepository.findRecommended()
                .stream()
                .map(productMapper::mapToResponse)
                .toList();
    }
}

