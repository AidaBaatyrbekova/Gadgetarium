package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.BrandRequest;
import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "The Brand")
@RequestMapping("/api/brands")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {

    BrandService brandService;

    @Operation(summary = "add new brand")
    @PostMapping("/create")
    public ResponseEntity<BrandResponse> create(@RequestBody BrandRequest brandRequest) {
        try {
            BrandResponse brandResponse = brandService.create(brandRequest);
            return new ResponseEntity<>(brandResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "delete brand by Id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            brandService.delete(id);
            return new ResponseEntity<>("Brand with id " + id + " successfully deleted", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Brand not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public List<BrandResponse> getAllBrand() {
        return brandService.getAll();
    }

    @Operation(summary = "get brand by Id")
    @GetMapping("/{id}")
    public BrandResponse findById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @Operation(summary = "Update the brand by Id")
    @PatchMapping("/update/{id}")
    public BrandResponse brandResponse(@PathVariable("id") Long id, @RequestBody BrandRequest brandRequest) {
        return brandService.update(id, brandRequest);
    }
}