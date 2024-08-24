package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {

    BrandService brandService;

    @Operation(summary = "add new brand")
    @PostMapping("/add/{brandName}")
    public Brand addBrand(@PathVariable String brandName, Principal principal) {
        return brandService.addBrand(brandName, principal);
    }
}