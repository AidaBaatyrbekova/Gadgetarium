package com.peaksoft.gadgetarium.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    Double rating;

    int discount;

    int price;

    LocalDate createDate;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "products")
    List<Basket> baskets;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "products")
    List<Order> orders;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "products")
    List<OrderHistory> orderHistories;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "products")
    List<Delivery> deliveries;

    @JsonIgnore
    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "brand_id")
    Brand brand;

    @OneToOne(mappedBy = "product")
    Feedback feedback;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "sub_category_id")
    SubCategory subCategory;
}