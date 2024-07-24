package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductCompareRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByBrandId(Long brandId);
    List<Product> findByOperationSystem(String operationSystem);
    List<Product> findByProductName(String productName);
    List<Product> findByScreen(String screen);
    List<Product> findByWeight(Integer weight);
    List<Product> findByPrice(Integer price);
    List<Product> findBySimCard(String simCard);
}
