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
    OperationMemory operationMemory;
    OperationSystem operationSystem;
    LocalDate createDate;
    Memory memory;
    Color color;
    Long categoryId;
    Long brandId;
    String operationSystemNum;
    String dateOfRelease;
    String processor;
    String guarantee;
    String screen;
    String simCard;
    String rating;
    int discount;
    int weight;
    int price;
}
