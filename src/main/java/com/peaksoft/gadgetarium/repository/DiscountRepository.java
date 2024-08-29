package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
}