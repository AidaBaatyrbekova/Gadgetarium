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

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "Product name cannot be blank")
    String productName;

    @NotNull(message = "Product status is required")
    ProductStatus productStatus;

    @NotNull(message = "Operation memory is required")
    OperationMemory operationMemory;

    @NotNull(message = "Operation system is required")
    OperationSystem operationSystem;

    @NotNull(message = "Create date is required")
    LocalDate createDate;

    @NotNull(message = "Memory is required")
    Memory memory;

    @NotNull(message = "Color is required")
    Color color;

    @NotNull(message = "SubCategory ID is required")
    Long subCategoryId;

    @NotNull(message = "Category ID is required")
    Long categoryId;

    @NotNull(message = "Brand ID is required")
    Long brandId;

    @NotBlank(message = "Operation system number is required")
    String operationSystemNum;

    @NotBlank(message = "Date of release is required")
    String dateOfRelease;

    @NotBlank(message = "Processor is required")
    @NotBlank(message = "Guarantee is required")
    String guarantee;

    @NotBlank(message = "Screen is required")
    String screen;

    @NotBlank(message = "SIM card information is required")
    String simCard;

    @NotBlank(message = "Processor is required")
    String processor;

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Max(value = 5, message = "Rating must be between 0 and 5")
    Double rating;

    @Min(value = 0, message = "Discount cannot be negative")
    int discount;

    @Min(value = 0, message = "Weight cannot be negative")
    int weight;

    @Min(value = 0, message = "Price cannot be negative")
    int price;
}
