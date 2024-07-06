package com.peaksoft.gadgetarium.model.entities;

import com.peaksoft.gadgetarium.model.enums.DeliveryType;
import com.peaksoft.gadgetarium.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String image;

    String address;

    @OneToOne(cascade = {
            CascadeType.MERGE , CascadeType.REFRESH , CascadeType.PERSIST , CascadeType.DETACH})
    @JoinColumn(name = "payment_id")
    Payment payment;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    @ManyToOne
    @JoinColumn (name = "user_id")
    User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    List<Product> products;

    Double amount;

    @Enumerated(EnumType.STRING)
    DeliveryType deliveryType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "order_order_histories",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "order_history_id"))
    List<OrderHistory> orderHistories;
}