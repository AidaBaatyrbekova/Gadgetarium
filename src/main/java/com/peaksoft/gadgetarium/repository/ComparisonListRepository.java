package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.ComparisonList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComparisonListRepository extends JpaRepository<ComparisonList,Long> {
    List<ComparisonList> findByCategory(Category category);

}
