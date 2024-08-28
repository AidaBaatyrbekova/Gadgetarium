package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "The Brand")
@RequiredArgsConstructor
@RequestMapping("/api/brands")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {

    BrandService brandService;

    @Operation(summary = "Create a new brand")
    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestParam String brandName, Principal principal) {
        Brand brand = brandService.addBrand(brandName, principal);
        return ResponseEntity.ok(brand);
    }
    @Operation(summary = "Get all brands")
    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands(Principal principal) {
        List<Brand> brands = brandService.getAllBrands(principal);
        return ResponseEntity.ok(brands);
    }

    @Operation(summary = "Update an existing brand")
    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestParam String newBrandName, Principal principal) {
        Brand updatedBrand = brandService.updateBrand(id, newBrandName, principal);
        return ResponseEntity.ok(updatedBrand);
    }

    @Operation(summary = "Delete a brand by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id, Principal principal) {
        brandService.deleteBrand(id, principal);
        return ResponseEntity.noContent().build();
    }
}