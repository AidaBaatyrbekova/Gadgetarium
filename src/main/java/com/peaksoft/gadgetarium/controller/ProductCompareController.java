package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.service.ProductCompareService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Product compare")
@RequestMapping("/api/compare")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareController {

    ProductCompareService productCompareService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addProductToComparison(@PathVariable Long productId) {
        try {
            productCompareService.addProductToComparison(productId);
            return ResponseEntity.ok("Product added to compare successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to compare");
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProductFromComparison(@PathVariable Long productId) {
        try {
            productCompareService.removeProductFromComparison(productId);
            return ResponseEntity.ok("Product removed from compare successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing product from compare");
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearComparisonList() {
        try {
            productCompareService.clearComparisonList();
            return ResponseEntity.ok("Compare list cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error clearing compare list");
        }
    }

    // Сравнить продукты по категории
    @GetMapping("/compareByCategory/{categoryId}/{showDifferences}")
    public String compareProductsByCategory(@PathVariable Long categoryId, @PathVariable boolean showDifferences) {
        return productCompareService.compareProductsByCategory(categoryId, showDifferences);
    }
}