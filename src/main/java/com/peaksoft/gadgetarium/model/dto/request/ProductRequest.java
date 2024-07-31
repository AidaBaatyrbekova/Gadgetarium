package com.peaksoft.gadgetarium.model.dto.request;

import com.peaksoft.gadgetarium.model.enums.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "Product name cannot be blank")
    String productName;

    @NotNull(message = "SubCategory ID is required")
    Long subCategoryId;

    @NotNull(message = "Brand ID is required")
    Long brandId;

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Max(value = 5, message = "Rating must be between 0 and 5")
    Double rating;

    @Min(value = 0, message = "Discount cannot be negative")
    int discount;

    ProductStatus productStatus;
    OperationMemory operationMemory;
    OperationSystem operationSystem;
    String operationSystemNum;
    String dateOfRelease;
    String guarantee;
    String simCard;
    String screen;
    String processor;
    Memory memory;
    Color color;
    int weight;
    int price;
}
