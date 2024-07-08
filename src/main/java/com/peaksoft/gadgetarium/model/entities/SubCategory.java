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
@Table(name = "sub_categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameOfSubCategory;

    @ManyToOne(cascade = {
            CascadeType.ALL})
    @JoinColumn(name = "category_id")
    Category categoryOfSubCategory;
}