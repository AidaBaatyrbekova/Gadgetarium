package com.peaksoft.gadgetarium.mapper;

import com.peaksoft.gadgetarium.model.dto.request.BrandRequest;
import com.peaksoft.gadgetarium.model.dto.response.BrandResponse;
import com.peaksoft.gadgetarium.model.entities.Brand;
import org.springframework.stereotype.Component;

@Component
    public class BrandMapper {

        public Brand mapToEntity(BrandRequest brandRequest) {
            Brand brand = new Brand();
            brand.setBrandName(brandRequest.getBrandName());
            return brand;
        }
        public BrandResponse mapToResponse(Brand brand) {
            return BrandResponse.builder()
                    .brandName(brand.getBrandName())
                    .build();
        }
    }