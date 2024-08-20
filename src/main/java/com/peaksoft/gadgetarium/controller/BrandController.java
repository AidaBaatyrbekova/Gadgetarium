package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.BrandRequest;
import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {

    BrandService brandService;

    @Operation(summary = "Add new brand")
    @PostMapping
    public ResponseEntity<BrandResponse> addBrand(@RequestBody BrandRequest brandRequest, Principal principal) {
        // Передаем запрос и объект Principal в сервис
        BrandResponse brandResponse = brandService.addBrand(brandRequest.getBrandName(), principal);

        // Возвращаем ответ с HTTP статусом 201 Created
        return new ResponseEntity<>(brandResponse, HttpStatus.CREATED);
    }
}