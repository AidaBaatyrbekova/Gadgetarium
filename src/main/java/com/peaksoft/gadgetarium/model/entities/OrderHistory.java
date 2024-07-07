package com.peaksoft.gadgetarium.model.entities;

import com.peaksoft.gadgetarium.model.enums.DeliveryStatus;
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
@Table(name = "delivery_histories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "orderHistory_products",
            joinColumns = @JoinColumn(name = "orderHistory_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    List<Product> products;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderHistory")
    Delivery delivery;

    @Enumerated(EnumType.ORDINAL)
    DeliveryStatus deliveryStatus;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "orderHistories")
    List<Order> orders;


    @ManyToOne(cascade = {
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    User user;

    LocalDate orderTime;
}