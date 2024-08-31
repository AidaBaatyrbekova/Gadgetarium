package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.BrandMapper;
import com.peaksoft.gadgetarium.model.dto.request.BrandRequest;
import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import com.peaksoft.gadgetarium.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandService {
    BrandMapper brandMapper;
    BrandRepository brandRepository;

    public BrandResponse create(BrandRequest brandRequest) {
        if (brandRepository.findByName(brandRequest.getBrandName()).isPresent()) {
            throw new IllegalArgumentException("Brand with name " + brandRequest.getBrandName() + " already exists.");
        }
        Brand brand = brandMapper.mapToEntity(brandRequest);
        brandRepository.save(brand);
        return brandMapper.mapToResponse(brand);
    }

    public List<BrandResponse> getAll() {
        List<BrandResponse> brandResponses = new ArrayList<>();
        for (Brand brand : brandRepository.findAll()) {
            brandResponses.add(brandMapper.mapToResponse(brand));
        }
        return brandResponses;
    }

    public void delete(Long brandById) {
        brandRepository.findById(brandById)
                .orElseThrow(() -> new EntityNotFoundException("Brand with id " + brandById + " not found"));
        brandRepository.deleteById(brandById);
    }

    public BrandResponse getBrandById(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("Brand with id " + brandId + " not found"));
        return brandMapper.mapToResponse(brand);
    }

    public BrandResponse update(Long id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand with id " + id + " not found"));
        brand.setBrandName(brandRequest.getBrandName());
        brandRepository.save(brand);
        return brandMapper.mapToResponse(brand);
    }
}