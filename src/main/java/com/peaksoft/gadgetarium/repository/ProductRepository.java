package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}