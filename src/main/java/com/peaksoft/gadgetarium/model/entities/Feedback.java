package com.peaksoft.gadgetarium.model.entities;

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
@Table(name = "feedbacks")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String image;

    String text;

    @OneToOne(cascade = {
            CascadeType.MERGE , CascadeType.REFRESH , CascadeType.PERSIST , CascadeType.DETACH})
    @JoinColumn(name = "product_id")
    Product product;
}