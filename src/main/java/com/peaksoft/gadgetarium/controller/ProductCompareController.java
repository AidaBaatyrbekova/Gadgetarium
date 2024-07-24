package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.ProductCompareRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.service.ProductCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/compare")
@RequiredArgsConstructor
public class ProductCompareController {

    ProductCompareService productCompareService;

    @PostMapping
    public List<ProductCompareResponse> compareProducts(@RequestBody ProductCompareRequest request) {
        return productCompareService.compareProducts(request);
    }
}
