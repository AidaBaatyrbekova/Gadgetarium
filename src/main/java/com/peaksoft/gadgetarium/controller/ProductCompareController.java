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

    @PostMapping("/create")
    public Long createComparisonList() {
        ComparisonList comparisonList = productCompareService.createComparisonList();
        return comparisonList.getId();
    }

    @PostMapping("/add/{comparisonListId}/{productId}")
    public void addProductToComparisonList(@PathVariable Long comparisonListId, @PathVariable Long productId) {
        productCompareService.addProductToComparisonList(comparisonListId, productId);
    }

    @PostMapping("/remove/{comparisonListId}/{productId}")
    public void removeProductFromComparisonList(@PathVariable Long comparisonListId, @PathVariable Long productId) {
        productCompareService.removeProductFromComparisonList(comparisonListId, productId);
    }

    @PostMapping("/clear/{comparisonListId}")
    public void clearComparisonList(@PathVariable Long comparisonListId) {
        productCompareService.clearComparisonList(comparisonListId);
    }

    @GetMapping("/compare/{comparisonListId}")
    public String compareProducts(@PathVariable Long comparisonListId) {
        return productCompareService.compareProducts(comparisonListId);
    }
}
