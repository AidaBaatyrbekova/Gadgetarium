package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:productName IS NULL OR p.productName = :productName) AND " +
            "(:color IS NULL OR p.color = :color) AND " +
            "(:memory IS NULL OR p.memory = :memory) AND " +
            "(:operationMemory IS NULL OR p.operationMemory = :operationMemory) AND " +
            "(:priceMin IS NULL OR p.price >= :priceMin) AND " +
            "(:priceMax IS NULL OR p.price <= :priceMax)")
    List<Product> filterProducts(
            @Param("productName") String productName,
            @Param("color") String color,
            @Param("memory") Integer memory,
            @Param("operationMemory") Integer operationMemory,
            @Param("priceMin") Integer priceMin,
            @Param("priceMax") Integer priceMax);
}