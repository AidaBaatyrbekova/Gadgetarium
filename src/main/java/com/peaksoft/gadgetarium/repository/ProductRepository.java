package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.model.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByPriceBetween(int minPrice, int maxPrice);

    List<Product> findBySubCategory_nameOfSubCategoryIgnoreCase(String categoryName);

    List<Product> findByBrand_BrandNameIgnoreCase(String brandName);
}
