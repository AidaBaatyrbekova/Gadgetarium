package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
}
