package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.ProductMapper;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.CategoryRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.request.ProductRequest;
import com.peaksoft.gadgetarium.response.ProductResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {
    final ProductMapper productMapper;
    final ProductRepository productRepository;
    CategoryRepository categoryRepository;
    BrandRepository brandRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.productMapper(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.mapToResponse(savedProduct);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToResponse).toList();
    }


    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        return productMapper.mapToResponse(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found by id:{}", id);
                    return new RuntimeException("Not found User by id" + id);
                });
        product.setProductName(request.getProductName());
        product.setProductStatus(request.getProductStatus());
        product.setMemory(request.getMemory());
        product.setColor(request.getColor());
        product.setOperationMemory(request.getOperationMemory());
        product.setScreen(request.getScreen());
        product.setOperationSystem(request.getOperationSystem());
        product.setOperationSystemNum(request.getOperationSystemNum());
        product.setDateOfRelease(request.getDateOfRelease());
        product.setSimCard(request.getSimCard());
        product.setProcessor(request.getProcessor());
        product.setWeight(request.getWeight());
        product.setGuarantee(request.getGuarantee());
        product.setPrice(request.getPrice());
        product.setCreateDate(request.getCreateDate());
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        category.ifPresent(product::setCategory);

        Optional<Brand> brand = brandRepository.findById(request.getBrandId());
        brand.ifPresent(product::setBrandOfProduct);
        return product;
    }


    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found User by id" + id));
        productRepository.delete(product);
        return "Successfully deleted user by id" + id;
    }


}
