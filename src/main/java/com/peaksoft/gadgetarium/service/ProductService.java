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
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductMapper productMapper;
    ProductRepository productRepository;
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

        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        category.ifPresent(product::setCategory);

        Optional<Brand> brand = brandRepository.findById(request.getBrandId());
        brand.ifPresent(product::setBrandOfProduct);
        return productRepository.save(product);
    }

    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found Product by id" + id));
        productRepository.delete(product);
        return "Successfully deleted product by id" + id;
    }
}
