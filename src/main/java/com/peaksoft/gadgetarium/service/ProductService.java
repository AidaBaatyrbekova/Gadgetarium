package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.SubCategoryRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                    log.warn("Product not found by id:{}", id);
                    return new RuntimeException("Not found product by id " + id);
                });
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

        // Обновляем SubCategory, если она указана
        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("SubCategory not found with id " + request.getSubCategoryId()));
            product.setSubCategory(subCategory);
        }

        // Обновляем Brand, если он указан
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found with id " + request.getBrandId()));
            product.setBrandOfProduct(brand);
        }


        return productRepository.save(product);
    }

    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found Product by id" + id));
        productRepository.delete(product);
        return "Successfully deleted product by id" + id;
    }
}
