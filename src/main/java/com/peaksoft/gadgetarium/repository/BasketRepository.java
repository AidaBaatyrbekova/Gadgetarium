package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}