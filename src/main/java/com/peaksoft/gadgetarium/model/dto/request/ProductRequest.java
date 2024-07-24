    package com.peaksoft.gadgetarium.model.dto.request;

    import com.peaksoft.gadgetarium.model.entities.SubCategory;
    import com.peaksoft.gadgetarium.model.enums.*;
    import lombok.AccessLevel;
    import lombok.Getter;
    import lombok.Setter;
    import lombok.experimental.FieldDefaults;

    import java.time.LocalDate;

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class ProductRequest {

        String productName;
        ProductStatus productStatus;
        OperationMemory operationMemory;
        OperationSystem operationSystem;
        LocalDate createDate;
        Memory memory;
        Color color;
        Long subCategoryId;
        Long categoryId;
        Long brandId;
        String operationSystemNum;
        String dateOfRelease;
        String processor;
        String guarantee;
        String screen;
        String simCard;
        int weight;
        int price;
    }
