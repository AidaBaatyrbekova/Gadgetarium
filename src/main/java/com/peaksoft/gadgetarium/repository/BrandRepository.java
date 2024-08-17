package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    // Проверка, существует ли бренд с таким названием
    boolean existsByBrandName(String brandName);
}
