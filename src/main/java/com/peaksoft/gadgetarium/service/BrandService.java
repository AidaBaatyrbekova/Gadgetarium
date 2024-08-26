package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandService {

    BrandRepository brandRepository;

    @Transactional
    public Brand addBrand(String brandName, Principal principal) {
        checkAdmin(principal);

        if (brandRepository.existsByBrandName(brandName)) {
            throw new IllegalArgumentException("Brand with name " + brandName + " already exists.");
        }

        Brand brand = new Brand();
        brand.setBrandName(brandName);
        return brandRepository.save(brand);
    }

    @Transactional(readOnly = true)
    public List<Brand> getAllBrands(Principal principal) {
        checkAdmin(principal);
        return brandRepository.findAll();
    }

    @Transactional
    public Brand updateBrand(Long id, String newBrandName, Principal principal) {
        checkAdmin(principal);

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.BRAND_NOT_FOUND));

        if (brandRepository.existsByBrandName(newBrandName)) {
            throw new IllegalArgumentException("Brand with name " + newBrandName + " already exists.");
        }

        brand.setBrandName(newBrandName);
        return brandRepository.save(brand);
    }

    @Transactional
    public ResponseEntity<String> deleteBrand(Long id, Principal principal) {
        checkAdmin(principal);

        if (!brandRepository.existsById(id)) {
            throw new NotFoundException(ExceptionMessage.BRAND_NOT_FOUND);
        }

        brandRepository.deleteById(id);
        return new ResponseEntity<>("Brand deleted successfully", HttpStatus.OK);
    }

    private void checkAdmin(Principal principal) {
        String userRole = principal.getName();
        if (!"ADMIN".equals(userRole)) {
            throw new AccessDeniedException("Only admins can perform this action.");
        }
    }
}