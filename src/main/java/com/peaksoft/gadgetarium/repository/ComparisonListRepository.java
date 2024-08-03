package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.ComparisonList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonListRepository extends JpaRepository<ComparisonList,Long> {
}
