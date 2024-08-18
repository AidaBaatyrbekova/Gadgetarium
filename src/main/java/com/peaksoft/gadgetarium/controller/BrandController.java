package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.BrandRequest;
import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Brand Name")
@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandController {
    BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponse> addBrand(@RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.addBrand(brandRequest);
        return new ResponseEntity<>(brandResponse, HttpStatus.CREATED);
    }
}