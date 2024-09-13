package com.peaksoft.gadgetarium.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {
    Long id;
    String brandName;
    String image;
}