package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.service.ProductCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compare")
@RequiredArgsConstructor
public class ProductCompareController {

    private final ProductCompareService productCompareService;

    // Добавить продукт в список сравнения
    @PostMapping("/add/{productId}")
    public void addProductToComparison(@PathVariable Long productId) {
        productCompareService.addProductToComparison(productId);
    }

    // Удалить продукт из списка сравнения
    @DeleteMapping("/remove/{productId}")
    public void removeProductFromComparison(@PathVariable Long productId) {
        productCompareService.removeProductFromComparison(productId);
    }

    // Очистить список сравнения
    @DeleteMapping("/clear")
    public void clearComparisonList() {
        productCompareService.clearComparisonList();
    }

    // Сравнить продукты по категории
    @GetMapping("/compareByCategory/{categoryId}/{showDifferences}")
    public String compareProductsByCategory(@PathVariable Long categoryId, @PathVariable boolean showDifferences) {
        return productCompareService.compareProductsByCategory(categoryId, showDifferences);
    }
}