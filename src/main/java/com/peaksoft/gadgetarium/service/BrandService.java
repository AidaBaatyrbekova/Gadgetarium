package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandService {

    BrandRepository brandRepository;

    @Transactional
    public Brand addBrand(String brandName, Principal principal) {

        // Проверка, что пользователь является администратором
        if (!isAdmin(principal)) {
            throw new AccessDeniedException("Only admins can add a brand.");
        }

        if (brandRepository.existsByBrandName(brandName)) {
            throw new IllegalArgumentException("Brand with name " + brandName + " already exists.");
        }

        Brand brand = new Brand();
        brand.setBrandName(brandName);

        brandRepository.save(brand);

        return brand;
    }

    private boolean isAdmin(Principal principal) {
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();

        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
    }
}