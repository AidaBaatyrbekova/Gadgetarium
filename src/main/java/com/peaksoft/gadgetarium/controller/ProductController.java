package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.service.CategoryService;
import com.peaksoft.gadgetarium.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;


    @PostMapping("/save")
    public ProductResponse save(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Long id, @RequestBody ProductRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return productService.updateProduct(id, request);
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
