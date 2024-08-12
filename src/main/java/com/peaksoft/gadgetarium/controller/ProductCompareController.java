package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.service.ProductCompareService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "Product compare")
@RequestMapping("/api/compare")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareController {

    ProductCompareService productCompareService;

    // Добавить продукт в список сравнения
    @PostMapping("/add")
    public ResponseEntity<String> addProductToComparison(@RequestParam Long productId, Principal principal) {
        return productCompareService.addProductToComparison(productId, principal);
    }

    @DeleteMapping
    public ResponseEntity<String> removeProductFromComparison(@RequestParam Long productId, Principal principal) {
        return productCompareService.removeProductFromComparison(productId, principal);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearComparisonList(Principal principal) {
        return productCompareService.clearComparisonList(principal);
    }

    // Получить все продукты из списка сравнения
    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAllProductsInComparison(Principal principal) {
        return productCompareService.getAllProductsInComparison(principal);
    }

    @GetMapping
    public ResponseEntity<String> compareProductsByCategory(
            @RequestParam(required = false) Long categoryId,
            @RequestParam boolean showDifferences,
            Principal principal) {
        return productCompareService.compareProductsByCategory(categoryId, showDifferences, principal);
    }
}