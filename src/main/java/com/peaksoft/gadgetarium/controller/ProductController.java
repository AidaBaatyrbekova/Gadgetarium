package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Product")
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;
    ProductRepository productRepository;

    @Operation(summary = "Save Product")
    @PostMapping("/save")
    public ProductResponse save(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @Operation(summary = "Update the Product by Id")
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable("id") Long id, @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @Operation(summary = "Find the Product by Id")
    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Find all Products by Id")
    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @Operation(summary = "Delete Products by Id")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }

    @Operation(summary = "Search Products by various criteria",
            description = "Search products by name, price range, category, or brand.")
    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand) {
        return productService.searchProducts(name, minPrice, maxPrice, category, brand);
    }
}