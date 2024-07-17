package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryByElectronicType(String electronicType) {
        return categoryRepository.findByElectronicType(electronicType);
    }

}