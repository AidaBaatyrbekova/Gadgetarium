package com.peaksoft.gadgetarium.model.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCompareResponse {
    Long id;
    Long categoryId;
    Long brandId;
    String operationSystem;
    String productName;
    String screen;
    String color;
    String memory;
    Integer weight;
    String simCard;
    boolean showDifferencesOnly;
}
