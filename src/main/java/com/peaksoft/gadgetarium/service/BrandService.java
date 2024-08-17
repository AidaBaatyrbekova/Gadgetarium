package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.dto.request.BrandRequest;
import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public BrandResponse addBrand(BrandRequest brandRequest) {
        // Проверка на существование бренда с таким именем
        if (brandRepository.existsByBrandName(brandRequest.getBrandName())) {
            throw new IllegalArgumentException("Brand with name " + brandRequest.getBrandName() + " already exists.");
        }

        // Создание нового бренда и сохранение в базу данных
        Brand brand = new Brand();
        brand.setBrandName(brandRequest.getBrandName());
        Brand savedBrand = brandRepository.save(brand);

        return new BrandResponse(savedBrand.getId(), savedBrand.getBrandName());
    }
}