package com.peaksoft.gadgetarium.model.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponse {
    Long id;
    String brandName;

    public BrandResponse(Long id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }
}