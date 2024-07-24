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
    String productName;
    String screen;
    String processor;
    int weight;
    int price;
    Long brandId;
}
