package com.peaksoft.gadgetarium.model.entities;

import com.peaksoft.gadgetarium.model.enums.Role;
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
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @Column(name = "last_Name")
    String lastName;

    @Column(unique = true)
    String email;

    @Column(name = "phone_number")
    String phoneNumber;

    String password;

    String confirmThePassword;

    String gender;

    String local;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "create_date")
    LocalDate createDate;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    List<OrderHistory> orderHistories;

    @OneToMany(mappedBy = "user")
    List<Favorite> favoriteProducts;
}