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
@Table(name = "deliveries")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    DeliveryStatus deliveryStatus;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "delivery_products",
            joinColumns = @JoinColumn(name = "delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    List<Product> products;



    @OneToOne(cascade = {
            CascadeType.MERGE , CascadeType.REFRESH , CascadeType.PERSIST , CascadeType.DETACH})
    @JoinColumn(name = "order_history_id")
    OrderHistory orderHistory;

    LocalDate orderTime;
}