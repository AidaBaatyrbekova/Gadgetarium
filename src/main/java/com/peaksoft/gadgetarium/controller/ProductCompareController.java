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

    // Добавление продукта в список сравнения пользователя
    @PostMapping("/add")
    public ResponseEntity<String> addProductCompare(@RequestParam Long userId, @RequestParam Long productId) {
        productCompareService.addProductToComparison(userId, productId);
        return ResponseEntity.ok("Product added to comparison list");
    }

    // Удаление продукта из списка сравнения пользователя
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProductFromCompare(@RequestParam Long userId, @RequestParam Long productId) {
        productCompareService.removeProductFromComparison(userId, productId);
        return ResponseEntity.ok("Product removed from comparison list.");
    }

    // Очистка списка сравнения пользователя
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCompareList(@RequestParam Long userId) {
        productCompareService.clearComparisonList(userId);
        return ResponseEntity.ok("Comparison list cleared.");
    }

    // Сравнение продуктов пользователя с фильтрацией по категории
    @GetMapping("/compareByCategory")
    public ResponseEntity<String> compareProductsByCategory(@RequestParam Long userId, @RequestParam Long categoryId, boolean showDifferences) {
        String result = productCompareService.compareProductsByCategory(userId, categoryId, showDifferences);
        return ResponseEntity.ok(result);
    }
}