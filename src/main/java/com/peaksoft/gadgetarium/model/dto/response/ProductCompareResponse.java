package com.peaksoft.gadgetarium.model.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCompareResponse {
    List<String> differences;

     public ProductCompareResponse() {}

     public ProductCompareResponse(List<String> differences) {
          this.differences = differences;
     }

}
