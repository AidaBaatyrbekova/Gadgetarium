package com.peaksoft.gadgetarium.model.entities;

import com.peaksoft.gadgetarium.model.enums.DeliveryStatus;
import com.peaksoft.gadgetarium.model.enums.DeliveryType;
import com.peaksoft.gadgetarium.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String applicationNumber;

    String image;

    String address;

    @OneToOne(cascade = {
            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "payment_id")
    Payment payment;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JoinColumn(name = "first_name")
    String firstName;

    @JoinColumn(name = "last_name")
    String lastName;

    String email;

    @JoinColumn(name = "phone_number")
    String phoneNumber;

    String photo;

    @CreationTimestamp
    LocalDateTime created;

    @Enumerated(EnumType.STRING)
    DeliveryStatus status;

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