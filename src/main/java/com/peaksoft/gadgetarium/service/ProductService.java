package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductMapper productMapper;
    ProductRepository productRepository;
    SubCategoryRepository subCategoryRepository;
    BrandRepository brandRepository;

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
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND_BY_ID + id));
        return productMapper.mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found by id: {}", id);
                    return new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND_BY_ID + id);
                });
        productMapper.updateProductFromRequest(request, product);

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
        return productMapper.mapToResponse(productRepository.save(product));
    }

    public String deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND_BY_ID + id));
        productRepository.delete(product);
        return "Successfully deleted product by id " + id;
    }
    public List<ProductResponse> searchProducts(String name, Integer minPrice, Integer maxPrice, String category, String brand) {
        if (name != null) {
            return searchByName(name);
        }
        if (minPrice != null && maxPrice != null) {
            if (minPrice > maxPrice) {
                throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
            }
            return searchByPriceRange(minPrice, maxPrice);
        }
        if (category != null) {
            return searchByCategory(category);
        }
        if (brand != null) {
            return searchByBrand(brand);
        }
        return List.of();
    }

    private List<ProductResponse> searchByName(String name) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(name);
        return products.stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    private List<ProductResponse> searchByPriceRange(int minPrice, int maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products.stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    private List<ProductResponse> searchByCategory(String categoryName) {
        List<Product> products = productRepository.findBySubCategory_SubCategoryNameIgnoreCase(categoryName);
        return products.stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    private List<ProductResponse> searchByBrand(String brandName) {
        List<Product> products = productRepository.findByBrand_BrandNameIgnoreCase(brandName);
        return products.stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());
    }
}
