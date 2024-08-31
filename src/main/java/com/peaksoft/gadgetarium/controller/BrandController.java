package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "The Brand")
@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {

    BrandService brandService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{Creat}")
    public ResponseEntity<Brand> createBrand(@RequestParam String brandName) {
        Brand brand = brandService.createBrand(brandName);
        return ResponseEntity.ok(brand);
    }
    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestParam String brandName) {
        Brand updatedBrand = brandService.updateBrand(id, brandName);
        return ResponseEntity.ok(updatedBrand);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        return brandService.deleteBrand(id);
    }
}