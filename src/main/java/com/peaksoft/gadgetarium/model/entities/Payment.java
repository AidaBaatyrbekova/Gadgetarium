package com.peaksoft.gadgetarium.model.entities;

import com.peaksoft.gadgetarium.model.enums.PaymentSystem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    PaymentSystem paymentSystems;

    String cardNumber;

    String  cvc;

    int monthDate;

    int yearDate;

    String userName;

    @OneToOne (mappedBy = "payment")
    Order order;
}