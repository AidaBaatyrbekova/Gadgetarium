package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.dto.request.BrandRequest;
import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public BrandResponse addBrand(BrandRequest brandRequest) {
        if (brandRepository.existsByBrandName(brandRequest.getBrandName())) {
            throw new IllegalArgumentException("Бренд с именем " + brandRequest.getBrandName() + " уже существует.");
        }

        Brand brand = new Brand();
        brand.setBrandName(brandRequest.getBrandName());
        Brand savedBrand = brandRepository.save(brand);

        return new BrandResponse(savedBrand.getId(), savedBrand.getBrandName());
    }
}