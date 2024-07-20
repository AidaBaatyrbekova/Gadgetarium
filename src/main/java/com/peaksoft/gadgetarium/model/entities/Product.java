package com.peaksoft.gadgetarium.model.entities;

import com.peaksoft.gadgetarium.model.enums.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String productName;

    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    Memory memory;

    @Enumerated(EnumType.STRING)
    Color color;

    @Enumerated(EnumType.STRING)
    OperationMemory operationMemory;

    String screen;

    @Enumerated(EnumType.STRING)
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

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    List<Basket> baskets;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    List<Favorite> favorites;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    List<Order> orders;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    List<OrderHistory> orderHistories;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    List<Delivery> deliveries;

    @ManyToOne(cascade = {
            CascadeType.ALL})
    @JoinColumn(name = "brand_id")
    Brand brandOfProduct;

    @OneToOne (mappedBy = "product")
    Feedback feedback;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    SubCategory subCategory;
}