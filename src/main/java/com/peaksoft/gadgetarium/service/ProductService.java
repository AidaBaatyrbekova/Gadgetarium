package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMassage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.model.enums.ProductStatus;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.SubCategoryRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID + id));
        return productMapper.mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found by id: {}", id);
                    return new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID + id);
                });
        productMapper.updateProductFromRequest(request, product);

        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                    .orElseThrow(() -> new NotFoundException(ExceptionMassage.SUB_CATEGORY_NOT_FOUND_WITH_ID + request.getSubCategoryId()));
            product.setSubCategory(subCategory);
        }
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new NotFoundException(ExceptionMassage.BRAND_NOT_FOUND_WITH_ID + request.getBrandId()));
            product.setBrand(brand);
        }
        return productMapper.mapToResponse(productRepository.save(product));
    }

    public String deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID + id));
        productRepository.delete(product);
        return "Successfully deleted product by id " + id;
    }

    public static class MainPage {
        List<ProductResponse> discountedProducts;
        List<ProductResponse> newArrivals;
        List<ProductResponse> recommendedProducts;

        public MainPage(List<ProductResponse> discountedProducts, List<ProductResponse> newArrivals,
                        List<ProductResponse> recommendedProducts) {
            this.discountedProducts = discountedProducts;
            this.newArrivals = newArrivals;
            this.recommendedProducts = recommendedProducts;
        }
    }

    public MainPage getMainPage() {
        List<ProductResponse> discountedProducts = findDiscountedProducts();
        List<ProductResponse> newArrivals = findNewDevices();
        List<ProductResponse> recommendedProducts = findRecommendedProducts();

        return new MainPage(discountedProducts, newArrivals, recommendedProducts);
    }

    public List<ProductResponse> findDiscountedProducts() {
        List<Product> products = productRepository.findDiscounted();
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findNewDevices() {
        List<Product> products = productRepository.findByProductStatus(ProductStatus.NEW_DEVICES);
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findRecommendedProducts() {
        List<Product> products = productRepository.findRecommended();
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse (product.getId(), product.getProductName(), product.getPrice(), product.getDiscount());
    }
}