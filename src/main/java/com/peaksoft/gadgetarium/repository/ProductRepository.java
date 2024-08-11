package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByPriceBetween(int minPrice, int maxPrice);

    List<Product> findBySubCategory_SubCategoryNameIgnoreCase(String categoryName);

    List<Product> findByBrand_BrandNameIgnoreCase(String brandName);
}
