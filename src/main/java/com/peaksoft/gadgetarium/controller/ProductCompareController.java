package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.Compare;
import com.peaksoft.gadgetarium.service.ProductCompareService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compare")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareController {

    ProductCompareService productCompareService;

    @PostMapping("/create/{categoryId}")
    public ResponseEntity<Compare> createComparisonList(@PathVariable Long categoryId) {
        Compare comparisonList = productCompareService.createComparisonList(categoryId);
        return ResponseEntity.ok(comparisonList);
    }

    @PostMapping("/add/{categoryId}/{productId}")
    public ResponseEntity<String> addProductToComparison(@PathVariable Long productId, @PathVariable Long categoryId) {
        productCompareService.addProductComparisonList(productId, categoryId);
        return ResponseEntity.ok("Product added to comparison list");
    }

    @DeleteMapping("/remove/{categoryId}/{productId}")
    public ResponseEntity<String> removeProductFromComparison(@PathVariable Long productId, @PathVariable Long categoryId) {
        productCompareService.removeProductComparisonList(productId, categoryId);
        return ResponseEntity.ok("Product removed from comparison list");
    }

    @DeleteMapping("/clear/{categoryId}")
    public ResponseEntity<String> clearComparisonList(@PathVariable Long categoryId) {
        productCompareService.clearComparisonList(categoryId);
        return ResponseEntity.ok("Comparison list cleared");
    }

    @PostMapping("/compare/{subCategoryId}/{showDifferences}")
    public ResponseEntity<String> compareProducts(@PathVariable Long subCategoryId, @PathVariable boolean showDifferences) {
        String comparisonResult = productCompareService.compareProducts(subCategoryId, showDifferences);
        return ResponseEntity.ok(comparisonResult);
    }
}