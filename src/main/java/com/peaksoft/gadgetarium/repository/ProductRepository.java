package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.enums.Color;
import com.peaksoft.gadgetarium.model.enums.Memory;
import com.peaksoft.gadgetarium.model.enums.OperationMemory;
import com.peaksoft.gadgetarium.model.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.discount > 0 and p.productStatus='SALES'")
    List<Product> findDiscounted();

    @Query("SELECT p FROM Product p WHERE p.productStatus = :status")
    List<Product> findByProductStatus(ProductStatus status);

    @Query("SELECT p FROM Product p WHERE p.productStatus = com.peaksoft.gadgetarium.model.enums." +
            "ProductStatus.RECOMMENDATIONS")
    List<Product> findRecommended();

    @Query("SELECT p FROM Product p " +
            "JOIN p.subCategory sc " +
            "JOIN p.brand b " +
            "WHERE " +
            "(:nameOfSubCategory IS NULL OR sc.nameOfSubCategory = :nameOfSubCategory) AND " +
            "(:brandName IS NULL OR b.brandName = :brandName) AND " +
            "(:color IS NULL OR p.color = :color) AND " +
            "(:memory IS NULL OR p.memory = :memory) AND " +
            "(:operationMemory IS NULL OR p.operationMemory = :operationMemory) AND " +
            "(:priceMin IS NULL OR p.price >= :priceMin) AND " +
            "(:priceMax IS NULL OR p.price <= :priceMax)")
    List<Product> filterProducts(
            @Param("nameOfSubCategory") String nameOfSubCategory,
            @Param("brandName") String brandName,
            @Param("color") Color color,
            @Param("memory") Memory memory,
            @Param("operationMemory") OperationMemory operationMemory,
            @Param("priceMin") Integer priceMin,
            @Param("priceMax") Integer priceMax);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByPriceBetween(int minPrice, int maxPrice);

    List<Product> findBySubCategory_nameOfSubCategoryIgnoreCase(String categoryName);

    List<Product> findByBrand_BrandNameIgnoreCase(String brandName);
}