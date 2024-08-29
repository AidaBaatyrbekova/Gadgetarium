package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandService {

    BrandRepository brandRepository;
    @Transactional
    public Brand createBrand(String brandName) {
        if (brandRepository.existsByBrandName(brandName)) {
            throw new IllegalArgumentException("Brand with this name already exists");
        }
        Brand brand = new Brand();
        brand.setBrandName(brandName);
        return brandRepository.save(brand);
    }

    @Transactional
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Transactional
    public Brand getBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.BRAND_NOT_FOUND));
    }

    @Transactional
    public Brand updateBrand(Long brandId, String newBrandName) {
        Brand brand = getBrandById(brandId);
        brand.setBrandName(newBrandName);
        return brandRepository.save(brand);
    }

    @Transactional
    public ResponseEntity<String> deleteBrand(Long brandId) {
        Brand brand = getBrandById(brandId);
        brandRepository.delete(brand);
        return new ResponseEntity<>("Brand deleted successfully", HttpStatus.OK);
    }
}