package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandService {

    BrandRepository brandRepository;

    @Transactional
    public BrandResponse addBrand(String brandName, Principal principal) {
        // Получаем аутентификацию текущего пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, является ли текущий пользователь администратором
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new SecurityException("У вас нет прав для добавления нового бренда.");
        }

        // Проверяем, существует ли уже бренд с таким именем
        if (brandRepository.existsByBrandName(brandName)) {
            throw new IllegalArgumentException("Бренд с именем " + brandName + " уже существует.");
        }

        // Создаем новый объект бренда и сохраняем его в базу данных
        Brand brand = new Brand();
        brand.setBrandName(brandName);

        Brand savedBrand = brandRepository.save(brand);

        // Возвращаем ответ с данными о новом бренде
        return new BrandResponse(savedBrand.getId(), savedBrand.getBrandName());
    }
}