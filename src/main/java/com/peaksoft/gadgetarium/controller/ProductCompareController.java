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

    @PostMapping("/create")
    public ResponseEntity<Compare> createComparisonList(@RequestParam Category categoryId) {
        Compare comparisonList = productCompareService.createComparisonList(categoryId.getId());
        return ResponseEntity.ok(comparisonList);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProductToComparison(@RequestParam Long productId, @RequestParam Long categoryId) {
        productCompareService.addProductComparisonList(productId, categoryId);
        return ResponseEntity.ok("Product added to comparison list");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProductFromComparison(
            @RequestParam Long productId,
            @RequestParam Long categoryId) {
        productCompareService.removeProductComparisonList(productId, categoryId);
        return ResponseEntity.ok("Product removed from comparison list");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearComparisonList(@RequestParam Long categoryId) {
        productCompareService.clearComparisonList(categoryId);
        return ResponseEntity.ok("Comparison list cleared");
    }

    @PostMapping("/compare")
    public ResponseEntity<String> compareProducts(
            @RequestParam Long subCategoryId,
            @RequestParam boolean showDifferences) {
        String comparisonResult = productCompareService.compareProducts(subCategoryId, showDifferences);
        return ResponseEntity.ok(comparisonResult);
    }
}