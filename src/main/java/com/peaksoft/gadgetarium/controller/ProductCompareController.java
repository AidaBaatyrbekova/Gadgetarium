package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.ProductCompareRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.service.ProductCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compare")
@RequiredArgsConstructor
public class ProductCompareController {

    ProductCompareService productCompareService;

    @GetMapping("/{categoryId}")
    public List<ProductCompareResponse> compareProducts(
            @PathVariable Long categoryId,
            @RequestParam boolean showDifferencesOnly) {
        return productCompareService.compareProducts(categoryId, showDifferencesOnly);
    }

}
