package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
