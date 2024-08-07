package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMassage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.Compare;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.SubCategory;
import com.peaksoft.gadgetarium.repository.CategoryRepository;
import com.peaksoft.gadgetarium.repository.CompareRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.SubCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareService {

    ProductRepository productRepository;
    CompareRepository comparisonListRepository;
    SubCategoryRepository subCategoryRepository;
    CategoryRepository categoryRepository;

    // Создаем новый список сравнения
    public Compare createComparisonList(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.CATEGORY_NOT_FOUND));
        Compare comparisonList = new Compare();
        comparisonList.setCategory(category);
        return comparisonListRepository.save(comparisonList);
    }

    // Добавление товара в список сравнения
    public void addProductComparisonList(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.CATEGORY_NOT_FOUND));

        Compare comparisonList = comparisonListRepository.findByCategory(category).stream()
                .findFirst()
                .orElseGet(() -> createComparisonList(categoryId));
        comparisonList.getProducts().add(product);
        comparisonListRepository.save(comparisonList);
    }

    // Удаление товара из списка сравнения
    public void removeProductComparisonList(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.PRODUCT_NOT_FOUND_BY_ID));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.CATEGORY_NOT_FOUND));

        Compare comparisonList = comparisonListRepository.findByCategory(category)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.COMPARISON_LIST_NOT_FOUND));

        comparisonList.getProducts().remove(product);
        comparisonListRepository.save(comparisonList);
    }

    // Очистка всего списка сравнения по категории
    public void clearComparisonList(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.CATEGORY_NOT_FOUND));
        Compare comparisonList = comparisonListRepository.findByCategory(category)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.COMPARISON_LIST_NOT_FOUND));

        comparisonList.getProducts().clear();
        comparisonListRepository.save(comparisonList);
    }

    // Сравнение товаров по подкатегории
    public String compareProducts(Long subCategoryId, boolean showDifferences) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new NotFoundException(ExceptionMassage.SUB_CATEGORY_NOT_FOUND));
        log.info("SubCategory found: {}", subCategory.getNameOfSubCategory());

        List<Product> products = productRepository.findAllBySubCategory(subCategory);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size(); j++) {
                Product p1 = products.get(i);
                Product p2 = products.get(j);

                Map<String, String[]> properties = getProductProperties(p1, p2);

                if (showDifferences) {
                    String comparisonResult = getDifferences(properties);
                    if (!comparisonResult.isEmpty()) {
                        result.append("Comparison between Product ").append(p1.getId())
                                .append(" and Product ").append(p2.getId()).append(":\n")
                                .append(comparisonResult).append("\n");
                    }
                } else {
                    result.append("Comparison between Product ").append(p1.getId())
                            .append(" and Product ").append(p2.getId())
                            .append(": \nThe products are compared.\n");
                }
            }
        }
        return result.toString();
    }

    // Метод для получения различий продуктов
    private String getDifferences(Map<String, String[]> properties) {
        StringBuilder differences = new StringBuilder();

        properties.forEach((key, value) -> {
            if (!value[0].equals(value[1])) {
                differences.append(key).append(": ").append(value[0]).append(" vs ").append(value[1]).append("\n");
            }
        });
        return differences.toString();
    }

    // Метод для создания карты свойств продуктов
    private Map<String, String[]> getProductProperties(Product p1, Product p2) {
        return Map.of(
                "Brand", new String[]{String.valueOf(p1.getBrand()), String.valueOf(p2.getBrand())},
                "Screen", new String[]{p1.getScreen(), p2.getScreen()},
                "Color", new String[]{String.valueOf(p1.getColor()), String.valueOf(p2.getColor())},
                "Operation System", new String[]{String.valueOf(p1.getOperationSystem()), String.valueOf(p2.getOperationSystem())},
                "Memory", new String[]{String.valueOf(p1.getMemory()), String.valueOf(p2.getMemory())},
                "Weight", new String[]{String.valueOf(p1.getWeight()), String.valueOf(p2.getWeight())},
                "Sim Card", new String[]{p1.getSimCard(), p2.getSimCard()}
        );
    }
}