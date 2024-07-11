package com.peaksoft.gadgetarium.request;

import com.peaksoft.gadgetarium.model.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductRequest {

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

    int price;

    LocalDate createDate;


    Long brandId;
}
