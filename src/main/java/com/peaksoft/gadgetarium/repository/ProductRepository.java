package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.discount > 0")
    List<Product> findDiscounted();

    @Query("SELECT p FROM Product p WHERE p.newArrival = true")
    List<Product> findNewArrivals();

    @Query("SELECT p FROM Product p WHERE p.recommended = true")
    List<Product> findRecommended();
}


