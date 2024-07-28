package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.service.ProductCompareService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compare")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareController {

    ProductCompareService productCompareService;

    @GetMapping("/{categoryId}")
    public List<ProductCompareResponse> compareProducts(
            @PathVariable Long categoryId,
            @RequestParam boolean showDifferencesOnly) {
        return productCompareService.compareProducts(categoryId, showDifferencesOnly);
    }
}