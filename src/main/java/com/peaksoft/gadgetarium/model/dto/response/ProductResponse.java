package com.peaksoft.gadgetarium.model.dto.response;

import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.enums.Color;
import com.peaksoft.gadgetarium.model.enums.Memory;
import com.peaksoft.gadgetarium.model.enums.OperationMemory;
import com.peaksoft.gadgetarium.model.enums.OperationSystem;
import com.peaksoft.gadgetarium.model.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String productName;
    ProductStatus productStatus;
    OperationMemory operationMemory;
    OperationSystem operationSystem;
    Long subCategoryId;
    Category category;
    LocalDate createDate;
    Memory memory;
    Color color;
    Long brandId;
    String operationSystemNum;
    String dateOfRelease;
    String processor;
    String guarantee;
    String screen;
    String simCard;
    Double rating;
    int discount;
    int weight;
    int price;
}
