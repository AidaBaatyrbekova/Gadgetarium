package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @Operation(summary = "add new brand")
    @PostMapping
    public BrandResponse addBrand(@PathVariable String brandName, Principal principal) {
        return brandService.addBrand(brandName, principal);
    }
}