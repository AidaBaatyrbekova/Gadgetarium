package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.AccessDeniedException;
import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import com.peaksoft.gadgetarium.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandService {

    BrandRepository brandRepository;
    UserRepository userRepository;

    @Transactional
    public BrandResponse addBrand(String brandName, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", principal.getName());
                    return new NotFoundException(ExceptionMessage.USER_NOT_FOUND);
                });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException(ExceptionMessage.USER_NOT_FOUND);
        }

        if (brandRepository.existsByBrandName(brandName)) {
            throw new IllegalArgumentException("Brand with name " + brandName + " already exists.");
        }

        Brand brand = new Brand();
        brand.setBrandName(brandName);

        Brand savedBrand = brandRepository.save(brand);

        return new BrandResponse(savedBrand.getId(), savedBrand.getBrandName());
    }
}
