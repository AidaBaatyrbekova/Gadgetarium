package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.entities.ComparisonList;
import com.peaksoft.gadgetarium.service.ProductCompareService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compare")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareController {

    ProductCompareService productCompareService;

    @PostMapping("/create/{categoryId}")
    public void createComparisonList(@PathVariable Long categoryId) {
        productCompareService.createComparisonList(categoryId);
    }

    @PostMapping("/add/{productId}/{categoryId}")
    public void addProductToComparisonList(@PathVariable Long productId, @PathVariable Long categoryId) {
        productCompareService.addProductToComparisonList(productId, categoryId);
    }

    @PostMapping("/remove/{productId}/{categoryId}")
    public void removeProductFromComparisonList(@PathVariable Long productId, @PathVariable Long categoryId) {
        productCompareService.removeProductFromComparisonList(productId, categoryId);
    }

    @PostMapping("/clear/{categoryId}")
    public void clearComparisonList(@PathVariable Long categoryId) {
        productCompareService.clearComparisonList(categoryId);
    }

    @GetMapping("/compare/{categoryId}")
    public String compareProducts(@PathVariable Long categoryId,
                                  @RequestParam(value = "showDifferences", defaultValue = "true") boolean showDifferences) {
        return productCompareService.compareProducts(categoryId, showDifferences);
    }

}
