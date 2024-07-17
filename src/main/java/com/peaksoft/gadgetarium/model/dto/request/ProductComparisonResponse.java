package com.peaksoft.gadgetarium.model.dto.request;

import com.peaksoft.gadgetarium.model.entities.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ProductComparisonResponse {
    Product product1;
    Product product2;
    String attribute;
    double comparisonResult;


}
