package com.peaksoft.gadgetarium.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {
    private Long id;
    private String brandName;

    public BrandResponse(Long id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }
}

