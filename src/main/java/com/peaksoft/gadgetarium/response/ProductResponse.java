package com.peaksoft.gadgetarium.response;

import com.peaksoft.gadgetarium.model.enums.*;
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

    Long categoryId;

    Memory memory;

    Color color;

    OperationMemory operationMemory;

    String screen;

    OperationSystem operationSystem;

    String operationSystemNum;

    String dateOfRelease;

    String simCard;

    String processor;

    int weight;

    String guarantee;

    String rating;

    int discount;

    int price;

    LocalDate createDate;

    Long brandId;
}
