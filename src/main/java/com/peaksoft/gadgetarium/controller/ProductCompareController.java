package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.service.ProductCompareService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Product compare")
@RequestMapping("/api/compare")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareController {

    ProductCompareService productCompareService;

    // Добавить продукт в список сравнения
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addProductToComparison(@PathVariable Long productId) {
        return productCompareService.addProductToComparison(productId);
    }

    // Удалить продукт из списка сравнения
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProductFromComparison(@PathVariable Long productId) {
        return productCompareService.removeProductFromComparison(productId);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearComparisonList() {
        return productCompareService.clearComparisonList();
    }

    // Сравнить продукты по категории
    @GetMapping("/compareByCategory/{categoryId}/{showDifferences}")
    public String compareProductsByCategory(@PathVariable Long categoryId, @PathVariable boolean showDifferences) {
        return productCompareService.compareProductsByCategory(categoryId, showDifferences);
    }
}