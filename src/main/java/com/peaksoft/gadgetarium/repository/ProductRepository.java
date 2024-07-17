package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

//    @Query("select product from Product product where product.productName=:name")
//    Optional<Product> findByName(@Param("name") String name);

    List<Product> findAllByCategoryId(Long categoryId);



}
