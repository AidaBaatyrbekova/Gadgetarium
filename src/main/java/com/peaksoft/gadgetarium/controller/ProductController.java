package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.request.ProductRequest;
import com.peaksoft.gadgetarium.response.ProductResponse;
import com.peaksoft.gadgetarium.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/products")
public class ProductController {
    ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Long id, @RequestBody ProductRequest request) {
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
